package reactiveandroid.widget

import android.widget.RadioGroup
import reactiveandroid.property.MutablePropertyOf
import reactiveandroid.property.mutablePropertyWith
import rx.Observable

/**
 * Created by Kittinun Vantasin on 7/16/15.
 */

//================================================================================
// Properties
//================================================================================

val RadioGroup.checkId: MutablePropertyOf<Int>
    get () {
        return mutablePropertyWith({ getCheckedRadioButtonId() }, { check(it) })
    }

//================================================================================
// Events
//================================================================================

val RadioGroup.checkedChange: Observable<Pair<RadioGroup, Int>>
    get() {
        return Observable.create { subscriber ->

            setOnCheckedChangeListener { radioGroup, checkId ->
                subscriber.onNext(radioGroup to checkId)
            }
        }
    }

