package property

import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.subjects.BehaviorSubject
import rx.subscriptions.CompositeSubscription
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 7/12/15.
 */

class MutablePropertyOf<T>(init: T) : MutableProperty<T> {

    override var value by Delegates.observable(init) { _, __, newValue ->
        subject.onNext(newValue)
    }

    override val observable: Observable<T>
        get() {
            return subject.asObservable()
        }

    private val subject = BehaviorSubject.create(init)


    fun bind(observable: Observable<T>): Subscription {
        val subscription = CompositeSubscription()

        subscription.add(observable.subscribe({ value ->
            this.value = value
        }, {
            //do nothing for error
        }, {
            subscription.unsubscribe()
        }))

        return subscription
    }

    fun bind(property: MutablePropertyOf<T>): Subscription {
        val subscription = CompositeSubscription()
        subscription.add(property.observable.subscribe ({ value ->
            this.value = value
        }, {
            //do nothing for error
        }, {
            subscription.unsubscribe()
        }))
        return subscription
    }

}

public fun mutablePropertyWith<T>(getter: () -> T, setter: (T) -> Unit): MutablePropertyOf<T> {
    val property = MutablePropertyOf(getter())
    property.observable.subscribe { value ->
        setter(value)
    }
    return property
}

