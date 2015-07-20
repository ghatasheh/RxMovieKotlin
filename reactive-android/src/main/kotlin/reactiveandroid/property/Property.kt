package reactiveandroid.property

import rx.Observable

/**
 * Created by Kittinun Vantasin on 7/12/15.
 */

interface Property<T> {

    public var value: T
        private set

    public val observable: Observable<T>

}

interface MutableProperty<T> : Property<T> {

    public override var value: T

}

