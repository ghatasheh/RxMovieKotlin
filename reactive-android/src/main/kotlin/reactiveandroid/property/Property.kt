package reactiveandroid.property

import rx.Observable

/**
 * Created by Kittinun Vantasin on 7/12/15.
 */

interface Property<T> {

    val value: T

    val observable: Observable<T>
}

interface MutableProperty<T> : Property<T> {

    override var value: T

}