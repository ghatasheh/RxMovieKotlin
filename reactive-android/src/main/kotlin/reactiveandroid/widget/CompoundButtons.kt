package reactiveandroid.widget

import android.widget.CompoundButton
import reactiveandroid.property.MutablePropertyOf
import reactiveandroid.view.mutablePropertyWith
import rx.Observable

/**
 * Created by Kittinun Vantasin on 7/16/15.
 */

//================================================================================
// Properties
//================================================================================

val CompoundButton.checked: MutablePropertyOf<Boolean>
    get () {
        return mutablePropertyWith({ isChecked() }, { setChecked(it) })
    }

//================================================================================
// Events
//================================================================================

val CompoundButton.checkedChange: Observable<Pair<CompoundButton, Boolean>>
    get () {
        return Observable.create { subscriber ->
            setOnCheckedChangeListener { compoundButton, isCheck ->
                subscriber.onNext(compoundButton to isCheck)
            }
        }
    }

