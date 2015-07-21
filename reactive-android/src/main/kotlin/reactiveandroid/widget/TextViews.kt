package reactiveandroid.widget

import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.widget.TextView
import reactiveandroid.property.MutablePropertyOf
import reactiveandroid.rx.Quad
import reactiveandroid.view.mutablePropertyWith
import rx.Observable

/**
 * Created by Kittinun Vantasin on 5/20/15.
 */

//================================================================================
// Properties
//================================================================================

public val TextView.text: MutablePropertyOf<CharSequence>
    get() {
        return mutablePropertyWith({ getText() }, { setText(it) })
    }

public val TextView.textResource: MutablePropertyOf<Int>
    get() {
        return mutablePropertyWith({ 0 }, { if (it > 0) setText(it) })
    }

//================================================================================
// Events
//================================================================================

public fun TextView.editorActions(consumed: Boolean): Observable<Triple<TextView, Int, KeyEvent>> {
    return Observable.create { subscriber ->
        setOnEditorActionListener { textView, actionId, keyEvent ->
            subscriber.onNext(Triple(textView, actionId, keyEvent))
            consumed
        }
    }
}

public fun TextView.textChange(): Observable<Quad<CharSequence, Int, Int, Int>> {
    return Observable.create { subscriber ->
        textWatcher.onTextChanged { charSequence, start, before, count ->
            subscriber.onNext(Quad(charSequence, start, before, count))
        }
    }
}

private val TextView.textWatcher: _TextView_TextWatcher
    get() {
        val listener = _TextView_TextWatcher()
        addTextChangedListener(listener)
        return listener
    }

private class _TextView_TextWatcher : TextWatcher {

    private var beforeTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null
    private var onTextChanged: ((CharSequence, Int, Int, Int) -> Unit)? = null
    private var afterTextChanged: ((Editable?) -> Unit)? = null

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = beforeTextChanged?.invoke(s, start, count, after)

    //proxy method
    fun beforeTextChanged(listener: (CharSequence?, Int, Int, Int) -> Unit) {
        beforeTextChanged = listener
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) = onTextChanged?.invoke(s, start, before, count)

    //proxy method
    fun onTextChanged(listener: (CharSequence, Int, Int, Int) -> Unit) {
        onTextChanged = listener
    }

    override fun afterTextChanged(editable: Editable?) = afterTextChanged?.invoke(editable)

    //proxy method
    fun afterTextChanged(listener: (Editable?) -> Unit) {
        afterTextChanged = listener
    }

}
