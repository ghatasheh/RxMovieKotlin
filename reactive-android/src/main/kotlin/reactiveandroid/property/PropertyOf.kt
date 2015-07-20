package reactiveandroid.property

import reactiveandroid.rx.liftWith
import reactiveandroid.rx.plusAssign
import rx.Observable
import rx.subjects.BehaviorSubject
import rx.subscriptions.CompositeSubscription
import kotlin.properties.Delegates
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty

/**
 * Created by Kittinun Vantasin on 7/12/15.
 */

class PropertyOf<T>(init: T) : Property<T> {

    private val subject = BehaviorSubject.create(init)

    public override val observable: Observable<T> = subject

    private val subscriptions = CompositeSubscription()

    constructor(property: Property<T>) : this(property.value) {
        subscriptions += property.observable.subscribe({
            value = it
        }, {
            subscriptions.unsubscribe()
        }, {
            subscriptions.unsubscribe()
        })
    }

    public override var value: T = init
        private set(value) {
            subject.onNext(value)
            $value = value
        }

}
