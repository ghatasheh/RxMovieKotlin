package reactiveandroid.property

import reactiveandroid.rx.plusAssign
import reactiveandroid.scheduler.AndroidSchedulers
import rx.Observable
import rx.Scheduler
import rx.Subscription
import rx.subjects.BehaviorSubject
import rx.subscriptions.CompositeSubscription
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 7/12/15.
 */

class MutablePropertyOf<T>(init: T) : MutableProperty<T> {

    private val subject = BehaviorSubject.create(init)

    private val subscriptions = CompositeSubscription()

    public override val observable: Observable<T> = subject

    public override var value by Delegates.observable(init) { _, __, newValue ->
        subject.onNext(newValue)
    }

    public fun bind(observable: Observable<T>): Subscription {
        subscriptions += observable.subscribe({
            value = it
        }, {
            subscriptions.unsubscribe()
        }, {
            subscriptions.unsubscribe()
        })
        return subscriptions
    }

    public fun bind(property: Property<T>): Subscription {
        subscriptions += property.observable.subscribe({
            value = it
        }, {
            subscriptions.unsubscribe()
        }, {
            subscriptions.unsubscribe()
        })
        return subscriptions
    }

}


