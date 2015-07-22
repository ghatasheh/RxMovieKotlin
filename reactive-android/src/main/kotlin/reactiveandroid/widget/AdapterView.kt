package reactiveandroid.widget

import android.view.View
import android.widget.AdapterView
import reactiveandroid.rx.Tuple4
import rx.Observable
import rx.Subscriber

/**
 * Created by Kittinun Vantasin on 7/22/15.
 */

public fun AdapterView<*>.itemClick(): Observable<Tuple4<AdapterView<*>, View, Int, Long>> {
    return Observable.create { subscriber ->
        setOnItemClickListener { adapterView, view, position, id ->
            subscriber.onNext(Tuple4(adapterView, view, position, id))
        }
    }
}

public fun AdapterView<*>.itemLongClick(consumed: Boolean): Observable<Tuple4<AdapterView<*>, View, Int, Long>> {
    return Observable.create { subscriber ->
        setOnItemLongClickListener { adapterView, view, position, id ->
            subscriber.onNext(Tuple4(adapterView, view, position, id))
            consumed
        }
    }
}

public fun AdapterView<*>.itemSelected(): Observable<Tuple4<AdapterView<*>, View, Int, Long>> {
    return Observable.create { subscriber ->
        itemSelected.onItemSelected { adapterView, view, position, id ->
            subscriber.onNext(Tuple4(adapterView!!, view!!, position, id))
        }
    }
}

public fun AdapterView<*>.nothingSelected(): Observable<AdapterView<*>> {
    return Observable.create { subscriber ->
        itemSelected.onNothingSelected {
            subscriber.onNext(it)
        }
    }
}

private val AdapterView<*>.itemSelected: _AdapterView_OnItemSelectedListener
    get() {
        val listener = _AdapterView_OnItemSelectedListener()
        setOnItemSelectedListener(listener)
        return listener
    }

private class _AdapterView_OnItemSelectedListener : AdapterView.OnItemSelectedListener {

    private var onNothingSelected: ((AdapterView<*>?) -> Unit)? = null

    private var onItemSelected: ((AdapterView<*>?, View?, Int, Long) -> Unit)? = null

    override fun onNothingSelected(parent: AdapterView<*>?) = onNothingSelected?.invoke(parent)

    //proxy method
    fun onNothingSelected(listener: (AdapterView<*>?) -> Unit) {
        onNothingSelected = listener
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) =  onItemSelected?.invoke(parent, view, position, id)

    //proxy method
    fun onItemSelected(listener: (AdapterView<*>?, View?, Int, Long) -> Unit) {
        onItemSelected = listener
    }

}
