package reactiveandroid.widget

import android.widget.ProgressBar
import reactiveandroid.property.MutablePropertyOf
import reactiveandroid.view.mutablePropertyWith

/**
 * Created by Kittinun Vantasin on 7/16/15.
 */

//================================================================================
// Properties
//================================================================================

public val ProgressBar.progress: MutablePropertyOf<Int>
    get() {
        return mutablePropertyWith({ getProgress() }, { setProgress(it) })
    }

public val ProgressBar.secondaryProgress: MutablePropertyOf<Int>
    get() {
        return mutablePropertyWith({ getSecondaryProgress() }, { setSecondaryProgress(it) })
    }

public val ProgressBar.max: MutablePropertyOf<Int>
    get() {
        return mutablePropertyWith({ getMax() }, { setMax(it) })
    }

public val ProgressBar.indeterminate: MutablePropertyOf<Boolean>
    get() {
        return mutablePropertyWith({ isIndeterminate() }, { setIndeterminate(it) })
    }
