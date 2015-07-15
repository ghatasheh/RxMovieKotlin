package property

import rx.Observable
import rx.Subscription
import rx.subjects.BehaviorSubject
import rx.subscriptions.CompositeSubscription

/**
 * Created by Kittinun Vantasin on 7/12/15.
 */

class PropertyOf<T>(init: T) : Property<T> {

    override val value: T = init

    override val observable: Observable<T>
        get() {
            return subject
        }

    private val subject = BehaviorSubject.create(init)

}