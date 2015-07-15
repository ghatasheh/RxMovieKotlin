package reactiveandroid.widget

import android.widget.SeekBar
import rx.Observable

/**
 * Created by Kittinun Vantasin on 7/15/15.
 */

//================================================================================
// Events
//================================================================================

val SeekBar.progressChange: Observable<Triple<SeekBar, Int, Boolean>>
    get() {
        return Observable.create { subscriber ->

            onProgressChangedListener.onProgressChanged { seekBar, progress, fromUser ->
                subscriber.onNext(Triple(seekBar, progress, fromUser))
            }
        }
    }

private val SeekBar.onProgressChangedListener: _SeekBar_OnSeekBarChangeListener
    get() {
        val listener = _SeekBar_OnSeekBarChangeListener()
        setOnSeekBarChangeListener(listener)
        return listener
    }

private class _SeekBar_OnSeekBarChangeListener : SeekBar.OnSeekBarChangeListener {

    private var onStartTrackingTouch: ((SeekBar) -> Unit)? = null
    private var onProgressChanged: ((SeekBar, Int, Boolean) -> Unit)? = null
    private var onStopTrackingTouch: ((SeekBar) -> Unit)? = null

    override fun onStartTrackingTouch(seekBar: SeekBar) = onStartTrackingTouch?.invoke(seekBar)

    //proxy method
    fun onStartTrackingTouch(listener: (SeekBar) -> Unit) {
        onStartTrackingTouch = listener
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) = onProgressChanged?.invoke(seekBar, progress, fromUser)

    //proxy method
    fun onProgressChanged(listener: (SeekBar, Int, Boolean) -> Unit) {
        onProgressChanged = listener
    }

    override fun onStopTrackingTouch(seekBar: SeekBar) = onStopTrackingTouch?.invoke(seekBar)

    //proxy method
    fun onStopTrackingTouch(listener: (SeekBar) -> Unit) {
        onStopTrackingTouch = listener
    }

}
