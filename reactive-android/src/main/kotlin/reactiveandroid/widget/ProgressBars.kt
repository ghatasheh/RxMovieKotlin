package reactiveandroid.widget

import android.widget.ProgressBar
import reactiveandroid.property.MutablePropertyOf
import reactiveandroid.property.mutablePropertyWith

/**
 * Created by Kittinun Vantasin on 7/16/15.
 */

//================================================================================
// Properties
//================================================================================

val ProgressBar.progress: MutablePropertyOf<Int>
    get() {
        return mutablePropertyWith({ getProgress() }, { setProgress(it) })
    }

val ProgressBar.secondaryProgress: MutablePropertyOf<Int>
    get() {
        return mutablePropertyWith({ getSecondaryProgress() }, { setSecondaryProgress(it) })
    }

val ProgressBar.max: MutablePropertyOf<Int>
    get() {
        return mutablePropertyWith({ getMax() }, { setMax(it) })
    }

val ProgressBar.indeterminate: MutablePropertyOf<Boolean>
    get() {
        return mutablePropertyWith({ isIndeterminate() }, { setIndeterminate(it) })
    }
