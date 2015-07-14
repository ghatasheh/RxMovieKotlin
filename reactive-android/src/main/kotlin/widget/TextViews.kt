package widget

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import property.MutablePropertyOf
import property.mutablePropertyWith
import rx.Observable

/**
 * Created by Kittinun Vantasin on 5/20/15.
 */

val TextView.text: MutablePropertyOf<CharSequence>
    get() {
        return mutablePropertyWith({ getText() }, { setText(it) })
    }

val TextView.textChanged: Observable<CharSequence>
    get() {
        return Observable.create<CharSequence> { subscriber ->
            onTextChangeListener.onTextChanged { charSequence, start, before, count ->
                subscriber.onNext(charSequence)
            }
        }
    }

private val TextView.onTextChangeListener: _TextView_TextWatcher
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
