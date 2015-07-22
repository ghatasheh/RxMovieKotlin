package reactiveandroid.widget

import android.widget.RadioGroup
import reactiveandroid.property.MutablePropertyOf
import reactiveandroid.rx.Tuple2
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

public fun RadioGroup.checkedChange(): Observable<Tuple2<RadioGroup, Int>> {
    return Observable.create { subscriber ->
        setOnCheckedChangeListener { radioGroup, checkId ->
            subscriber.onNext(Tuple2(radioGroup, checkId))
        }
    }
}

