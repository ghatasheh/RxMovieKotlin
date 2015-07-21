package reactiveandroid.view

import android.view.DragEvent
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import reactiveandroid.property.MutablePropertyOf
import reactiveandroid.scheduler.AndroidSchedulers
import rx.Observable

/**
 * Created by Kittinun Vantasin on 7/12/15.
 */

//================================================================================
// Properties
//================================================================================

public val View.activated: MutablePropertyOf<Boolean>
    get() {
        return mutablePropertyWith({ isActivated() }, { setActivated(it) })
    }

public val View.alpha: MutablePropertyOf<Float>
    get() {
        return mutablePropertyWith({ getAlpha() }, { setAlpha(it) })
    }

public val View.backgroundResource: MutablePropertyOf<Int>
    get() {
        return mutablePropertyWith({ 0 }, { if (it > 0) setBackgroundResource(it) })
    }

public val View.clickable: MutablePropertyOf<Boolean>
    get() {
        return mutablePropertyWith({ isClickable() }, { setClickable(it) })
    }

public val View.enabled: MutablePropertyOf<Boolean>
    get() {
        return mutablePropertyWith({ isEnabled() }, { setEnabled(it) })
    }

public val View.focusable: MutablePropertyOf<Boolean>
    get() {
        return mutablePropertyWith({ false }, { setFocusable(it) })
    }

public val View.pressed: MutablePropertyOf<Boolean>
    get() {
        return mutablePropertyWith({ isPressed() }, { setPressed(it) })
    }

public val View.selected: MutablePropertyOf<Boolean>
    get() {
        return mutablePropertyWith({ isSelected() }, { setSelected(it) })
    }

public val View.visibility: MutablePropertyOf<Int>
    get() {
        return mutablePropertyWith({ getVisibility() }, { setVisibility(it) })
    }

//================================================================================
// Events
//================================================================================

public fun View.click(): Observable<View> {
    return Observable.create { subscriber ->
        if (hasOnClickListeners()) setOnClickListener(null)

        setOnClickListener {
            subscriber.onNext(it)
        }
    }
}

public fun View.drag(consumed: Boolean): Observable<Pair<View, DragEvent>> {
    return Observable.create { subscriber ->
        setOnDragListener { view, dragEvent ->
            subscriber.onNext(view to dragEvent)
            consumed
        }
    }
}

public fun View.focusChange(): Observable<Pair<View, Boolean>> {
    return Observable.create { subscriber ->
        if (getOnFocusChangeListener() != null) setOnClickListener(null)

        setOnFocusChangeListener { view, hasFocus ->
            subscriber.onNext(view to hasFocus)
        }
    }
}

public fun View.key(consumed: Boolean): Observable<Triple<View, Int, KeyEvent>> {
    return Observable.create { subscriber ->
        setOnKeyListener { view, keyCode, keyEvent ->
            subscriber.onNext(Triple(view, keyCode, keyEvent))
            consumed
        }
    }
}

public fun View.longClick(consumed: Boolean): Observable<View> {
    return Observable.create { subscriber ->
        setOnLongClickListener {
            subscriber.onNext(it)
            consumed
        }
    }
}

public fun View.touch(consumed: Boolean): Observable<Pair<View, MotionEvent>> {
    return Observable.create { subscriber ->
        setOnTouchListener { view, motionEvent ->
            subscriber.onNext(view to motionEvent)
            consumed
        }
    }
}

//================================================================================
// Util
//================================================================================

public fun mutablePropertyWith<T>(getter: () -> T, setter: (T) -> Unit): MutablePropertyOf<T> {
    val property = MutablePropertyOf(getter())
    property.observable.observeOn(AndroidSchedulers.mainThreadScheduler()).subscribe {
        setter(it)
    }
    return property
}
