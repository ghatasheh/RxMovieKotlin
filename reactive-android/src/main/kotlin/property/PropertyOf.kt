package property

import rx.Observable
import rx.Subscriber
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 7/12/15.
 */

class PropertyOf<T>(init: T) : Property<T> {

    override val value: T = init

    override val observable: Observable<T>

    private var subscriber: Subscriber<in T> by Delegates.notNull()

    init {
        observable = Observable.create { s ->
            subscriber = s
            s.onNext(value)
            s.onCompleted()
        }
    }

}