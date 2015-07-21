package reactiveandroid.widget

import android.widget.RadioGroup
import reactiveandroid.property.MutablePropertyOf
import reactiveandroid.view.mutablePropertyWith
import rx.Observable

/**
 * Created by Kittinun Vantasin on 7/16/15.
 */

//================================================================================
// Properties
//================================================================================

public val RadioGroup.checkId: MutablePropertyOf<Int>
    get() {
        return mutablePropertyWith({ getCheckedRadioButtonId() }, { check(it) })
    }

//================================================================================
// Events
//================================================================================

public fun RadioGroup.checkedChange(): Observable<Pair<RadioGroup, Int>> {
    return Observable.create { subscriber ->
        setOnCheckedChangeListener { radioGroup, checkId ->
            subscriber.onNext(radioGroup to checkId)
        }
    }
}

