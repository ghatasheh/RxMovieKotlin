package reactiveandroid.support.v7.widget

import android.support.v7.widget.RecyclerView
import rx.Observable

/**
 * Created by Kittinun Vantasin on 7/16/15.
 */

//================================================================================
// Events
//================================================================================

val RecyclerView.scrollStateChange: Observable<Pair<RecyclerView, Int>>
    get() {
        return Observable.create { subscriber ->
            onScrollListener.onScrollStateChanged { view, newState ->
                subscriber.onNext(view to newState)
            }
        }
    }

val RecyclerView.scrolled: Observable<Triple<RecyclerView, Int, Int>>
    get() {
        return Observable.create { subscriber ->
            onScrollListener.onScrolled { view, dx, dy ->
                subscriber.onNext(Triple(view, dx, dy))
            }
        }
    }

private val RecyclerView.onScrollListener: _RecyclerView_OnScrollListener
    get() {
        val listener = _RecyclerView_OnScrollListener()
        addOnScrollListener(listener)
        return listener
    }

private class _RecyclerView_OnScrollListener : RecyclerView.OnScrollListener() {

    var onScrolled: ((RecyclerView, Int, Int) -> Unit)? = null
    var onScrollStateChanged: ((RecyclerView, Int) -> Unit)? = null

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) = onScrolled?.invoke(recyclerView, dx, dy)

    //proxy method
    fun onScrolled(listener: (RecyclerView, Int, Int) -> Unit) {
        onScrolled = listener
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) = onScrollStateChanged?.invoke(recyclerView, newState)

    //proxy method
    fun onScrollStateChanged(listener: (RecyclerView, Int) -> Unit) {
        onScrollStateChanged = listener
    }

}
