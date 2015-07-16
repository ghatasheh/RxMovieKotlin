package reactiveandroid.view

import android.util.Log
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import reactiveandroid.property.MutablePropertyOf
import reactiveandroid.property.mutablePropertyWith
import rx.Observable

/**
 * Created by Kittinun Vantasin on 7/12/15.
 */

//================================================================================
// Properties
//================================================================================

val View.enabled: MutablePropertyOf<Boolean>
    get() {
        return mutablePropertyWith({ isEnabled() }, { setEnabled(it) })
    }

val View.visibility: MutablePropertyOf<Int>
    get() {
        return mutablePropertyWith({ getVisibility() }, { setVisibility(it) })
    }

val View.activated: MutablePropertyOf<Boolean>
    get() {
        return mutablePropertyWith({ isActivated() }, { setActivated(it) })
    }

val View.clickable: MutablePropertyOf<Boolean>
    get() {
        return mutablePropertyWith({ isClickable() }, { setClickable(it) })
    }

val View.pressed: MutablePropertyOf<Boolean>
    get() {
        return mutablePropertyWith({ isPressed() }, { setPressed(it) })
    }

val View.selected: MutablePropertyOf<Boolean>
    get() {
        return mutablePropertyWith({ isSelected() }, { setSelected(it) })
    }

val View.alpha: MutablePropertyOf<Float>
    get() {
        return mutablePropertyWith({ getAlpha() }, { setAlpha(it) })
    }

//================================================================================
// Events
//================================================================================

val View.click: Observable<View>
    get () {
        return Observable.create { subscriber ->
            if (hasOnClickListeners()) Log.w("Reactive-Android", "View.click hijacks control's on click listener")

            setOnClickListener {
                subscriber.onNext(it)
            }
        }
    }

val View.focusChange: Observable<Pair<View, Boolean>>
    get () {
        return Observable.create { subscriber ->
            if (getOnFocusChangeListener() != null) Log.w("Reactive-Android", "View.focusChange hijacks control's on focus listener")

            setOnFocusChangeListener { view, hasFocus ->
                subscriber.onNext(view to hasFocus)
            }
        }
    }

val View.drag: Observable<Pair<View, DragEvent>>
    get () {
        return Observable.create { subscriber ->

            setOnDragListener { view, dragEvent ->
                subscriber.onNext(view to dragEvent)
                true
            }

        }
    }

val View.longClick: Observable<View>
    get () {
        return Observable.create { subscriber ->

            setOnLongClickListener {
                subscriber.onNext(it)
                true
            }
        }
    }

val View.touch: Observable<Pair<View, MotionEvent>>
    get () {
        return Observable.create { subscriber ->

            setOnTouchListener { view, motionEvent ->
                subscriber.onNext(view to motionEvent)
                true
            }
        }
    }
