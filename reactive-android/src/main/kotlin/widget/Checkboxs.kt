package widget

import android.widget.CheckBox
import property.MutablePropertyOf
import property.mutablePropertyWith

/**
 * Created by Kittinun Vantasin on 7/12/15.
 */

val CheckBox.checked: MutablePropertyOf<Boolean>
    get() {
        return mutablePropertyWith({ isChecked() }, { setChecked(it) })
    }
 
