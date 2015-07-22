package reactiveandroid.widget

import android.view.View
import android.widget.AdapterView
import reactiveandroid.rx.Tuple4
import rx.Observable

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
