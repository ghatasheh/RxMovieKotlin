package reactiveandroid.support.v7.widget

import android.support.v7.widget.RecyclerView
import android.util.Log
import rx.Observable

/**
 * Created by Kittinun Vantasin on 7/15/15.
 */

//================================================================================
// Events
//================================================================================

val <T : RecyclerView.Adapter<U>, U> T.dataChange: Observable<Unit>
    get() {
        return Observable.create { subscriber ->
            val listener = _RecyclerView_AdapterDataObserver()

            listener.onChanged {
                subscriber.onNext(Unit)
            }

            registerAdapterDataObserver(listener)
        }
    }

val <T : RecyclerView.Adapter<U>, U> T.dataObserver: Observable<Triple<AdapterDataObserverEvent, Int, Int>>
    get() {
        return Observable.create { subscriber ->
            val listener = _RecyclerView_AdapterDataObserver()

            listener.onItemRangeChanged { start, count ->
                subscriber.onNext(Triple(AdapterDataObserverEvent.Change, start, count))
            }

            listener.onItemRangeInserted { start, count ->
                subscriber.onNext(Triple(AdapterDataObserverEvent.Insert, start, count))
            }

            listener.onItemRangeMoved { from, to, count ->
                subscriber.onNext(Triple(AdapterDataObserverEvent.Move, from, to))
            }

            listener.onItemRangeRemoved { start, count ->
                subscriber.onNext(Triple(AdapterDataObserverEvent.Remove, start, count))
            }

            registerAdapterDataObserver(listener)
        }
    }

enum class AdapterDataObserverEvent(val value: Int) {

    Insert(1),
    Move(2),
    Remove(3),
    Change(8)

}

private class _RecyclerView_AdapterDataObserver : RecyclerView.AdapterDataObserver() {

    private var onChanged: (() -> Unit)? = null
    private var onItemRangeChanged: ((Int, Int) -> Unit)? = null
    private var onItemRangeInserted: ((Int, Int) -> Unit)? = null
    private var onItemRangeMoved: ((Int, Int, Int) -> Unit)? = null
    private var onItemRangeRemoved: ((Int, Int) -> Unit)? = null

    override fun onChanged() = onChanged?.invoke()

    //proxy method
    fun onChanged(listener: () -> Unit) {
        onChanged = listener
    }

    override fun onItemRangeChanged(positionStart: Int, itemCount: Int) = onItemRangeChanged?.invoke(positionStart, itemCount)

    //proxy method
    fun onItemRangeChanged(listener: (Int, Int) -> Unit) {
        onItemRangeChanged = listener
    }

    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) = onItemRangeInserted?.invoke(positionStart, itemCount)

    //proxy method
    fun onItemRangeInserted(listener: (Int, Int) -> Unit) {
        onItemRangeInserted = listener
    }

    override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) = onItemRangeMoved?.invoke(fromPosition, toPosition, itemCount)

    //proxy method
    fun onItemRangeMoved(listener: (Int, Int, Int) -> Unit) {
        onItemRangeMoved = listener
    }

    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) = onItemRangeRemoved?.invoke(positionStart, itemCount)

    //proxy method
    fun onItemRangeRemoved(listener: (Int, Int) -> Unit) {
        onItemRangeRemoved = listener
    }

}
