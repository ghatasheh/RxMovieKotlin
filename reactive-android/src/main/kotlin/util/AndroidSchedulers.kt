package util

import android.os.Handler
import android.os.Looper
import rx.Scheduler
import rx.Subscription
import rx.functions.Action0
import rx.internal.schedulers.ScheduledAction
import rx.subscriptions.CompositeSubscription
import rx.subscriptions.Subscriptions
import java.util.concurrent.TimeUnit

/**
 * Created by Kittinun Vantasin on 7/14/15.
 */

class AndroidSchedulers(val handler: Handler) : Scheduler() {

    companion object {

        fun mainThreadScheduler() = AndroidSchedulers(Handler(Looper.getMainLooper()))

    }

    override fun createWorker() = HandlerWorker(handler)

    private class HandlerWorker(val handler: Handler) : Scheduler.Worker() {

        val compositeSubscription = CompositeSubscription()

        override fun unsubscribe() {
            compositeSubscription.unsubscribe()
        }

        override fun isUnsubscribed(): Boolean {
            return compositeSubscription.isUnsubscribed()
        }

        override fun schedule(action: Action0?) = schedule(action, 0, TimeUnit.MILLISECONDS)


        override fun schedule(action: Action0?, delayTime: Long, unit: TimeUnit): Subscription? {
            val scheduledAction = ScheduledAction(action)
            scheduledAction.add(Subscriptions.create {
                handler.removeCallbacks(scheduledAction)
            })

            scheduledAction.addParent(compositeSubscription)
            compositeSubscription.add(scheduledAction)

            handler.postDelayed(scheduledAction, unit.toMillis(delayTime))
            return scheduledAction
        }

    }
}
