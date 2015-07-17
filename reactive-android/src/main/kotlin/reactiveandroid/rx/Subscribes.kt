package reactiveandroid.rx

import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Created by Kittinun Vantasin on 7/14/15.
 */

fun <T, U> U.liftObservable(o: Observable<T>, onNext: U.(T) -> Unit): Subscription {
    val subscriptions = CompositeSubscription()
    subscriptions += o.subscribe(object : Subscriber<T>(){

        override fun onError(e: Throwable?) {
        }

        override fun onCompleted() {
            subscriptions.unsubscribe()
        }

        override fun onNext(t: T) {
            onNext(t)
        }
    })
    return subscriptions
}
