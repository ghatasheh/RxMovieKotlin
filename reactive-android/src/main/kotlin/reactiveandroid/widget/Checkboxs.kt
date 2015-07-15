package reactiveandroid.widget

import android.widget.CheckBox
import reactiveandroid.property.MutablePropertyOf
import reactiveandroid.property.mutablePropertyWith

/**
 * Created by Kittinun Vantasin on 7/12/15.
 */

val CheckBox.checked: MutablePropertyOf<Boolean>
    get() {
        return mutablePropertyWith({ isChecked() }, { setChecked(it) })
    }
 
