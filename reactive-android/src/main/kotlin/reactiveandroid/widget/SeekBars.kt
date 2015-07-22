package reactiveandroid.widget

import android.widget.SeekBar
import reactiveandroid.rx.Tuple3
import rx.Observable

/**
 * Created by Kittinun Vantasin on 7/15/15.
 */

//================================================================================
// Events
//================================================================================

public fun SeekBar.progressChange(): Observable<Tuple3<SeekBar, Int, Boolean>> {
    return Observable.create { subscriber ->
        onProgressChangedListener.onProgressChanged { seekBar, progress, fromUser ->
            subscriber.onNext(Tuple3(seekBar, progress, fromUser))
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
