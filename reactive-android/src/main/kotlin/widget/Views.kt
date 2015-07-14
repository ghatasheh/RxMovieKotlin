package widget

import android.view.View
import property.MutablePropertyOf
import property.mutablePropertyWith

/**
 * Created by Kittinun Vantasin on 7/12/15.
 */

val View.enabled: MutablePropertyOf<Boolean>
    get() {
        return mutablePropertyWith({ isEnabled() }, { setEnabled(it) })
    }

val View.visibility: MutablePropertyOf<Int>
    get() {
        return mutablePropertyWith({ getVisibility() }, { setVisibility(it) })
    }
 
