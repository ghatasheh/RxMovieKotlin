package reactiveandroid.widget

import android.widget.ImageView
import reactiveandroid.property.MutablePropertyOf
import reactiveandroid.view.mutablePropertyWith

/**
 * Created by Kittinun Vantasin on 7/21/15.
 */

//================================================================================
// Properties
//================================================================================

public val ImageView.imageResource: MutablePropertyOf<Int>
    get() {
        return mutablePropertyWith({ 0 }, { if (it > 0) setImageResource(it) })
    }
 
