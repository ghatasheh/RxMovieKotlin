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

public val CompoundButton.checked: MutablePropertyOf<Boolean>
    get() {
        return mutablePropertyWith({ isChecked() }, { setChecked(it) })
    }

//================================================================================
// Events
//================================================================================

public fun CompoundButton.checkedChange(): Observable<Pair<CompoundButton, Boolean>> {
    return Observable.create { subscriber ->
        setOnCheckedChangeListener { compoundButton, isChecked ->
            subscriber.onNext(compoundButton to isChecked)
        }
    }
}

