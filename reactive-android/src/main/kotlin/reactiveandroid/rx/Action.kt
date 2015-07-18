package reactiveandroid.rx

import reactiveandroid.property.Property
import reactiveandroid.property.PropertyOf
import rx.Observable

/**
 * Created by Kittinun Vantasin on 7/17/15.
 */

class Action<T, U>(isEnabled: Property<Boolean>, execution: (T) -> Observable<U>) {

    val startingAsEnabled: PropertyOf<Boolean>

    init {
        startingAsEnabled = PropertyOf(isEnabled)
    }

}
