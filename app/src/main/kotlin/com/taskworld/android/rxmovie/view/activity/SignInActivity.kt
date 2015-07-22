package com.taskworld.android.rxmovie.view.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.taskworld.android.rxmovie.R
import com.taskworld.android.rxmovie.presentation.presenter.SignInPresenter
import com.taskworld.android.rxmovie.presentation.viewaction.SignInViewAction
import com.taskworld.android.rxmovie.util.TAG
import fuel.util.build
import kotlinx.android.synthetic.activity_sign_in.*
import reactiveandroid.rx.*
import reactiveandroid.scheduler.AndroidSchedulers
import reactiveandroid.view.click
import reactiveandroid.view.enabled
import reactiveandroid.view.focusChange
import reactiveandroid.view.visibility
import reactiveandroid.widget.text
import reactiveandroid.widget.textChange
import rx.Observable
import rx.subscriptions.CompositeSubscription
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 7/10/15.
 */

class SignInActivity : AppCompatActivity(), SignInViewAction {

    //presenter
    val presenter = SignInPresenter(this)

    val clearButtonVisibility by Delegates.lazy { signInClearButton.visibility }

    val subscriptions = CompositeSubscription()

    override fun onCreate(savedInstanceState: Bundle?) {
        super<AppCompatActivity>.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_in)

        bindObservables()

        val action = presenter.requestSignInAction()
        subscriptions += signInGoButton.enabled.bind(action.enabled)
        subscriptions += signInGoButton.click().subscribe {
            action.execute(signInEmailEdit.getText().toString() to signInPasswordEdit.getText().toString())

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0)
        }

        subscriptions += signInProgress.visibility.bind(action.executing.observable.map { if (it) View.VISIBLE else View.GONE })

        subscriptions += signInClearButton.click().liftWith(this, ::handleClearButtonClicked)
        subscriptions += Observable.merge<Boolean>(signInEmailEdit.focusChange().tupleSecond(), signInPasswordEdit.focusChange().tupleSecond()).liftWith(this, ::checkFocus)

        signInEmailEdit.focusChange().subscribe { v, b ->
            Log.e(TAG, b.toString())
        }
    }

    fun bindObservables() {
        subscriptions += presenter.email.bind(signInEmailEdit.textChange().tupleFirst())
        subscriptions += presenter.pass.bind(signInPasswordEdit.textChange().tupleFirst())

        subscriptions += clearButtonVisibility.bind(presenter.clearVisible)

        subscriptions += signInTokenText.text.bind(presenter.token)
    }

    fun handleClearButtonClicked(_: View) {
        signInEmailEdit.setText("")
        signInPasswordEdit.setText("")
    }

    fun checkFocus(isFocus: Boolean) {
        Log.e(TAG, "focus: $isFocus")
    }

    override fun onStart() {
        super<AppCompatActivity>.onStart()
        presenter.active = true
    }

    override fun onStop() {
        super<AppCompatActivity>.onStop()
        subscriptions.unsubscribe()
        presenter.active = false
    }

    //================================================================================
    // SignInViewAction
    //================================================================================

    override fun showSignInSuccess() {
        Toast.makeText(this, getString(R.string.sign_in_success), Toast.LENGTH_SHORT).show()
    }

    override fun showSignInFailure(message: String) {
        build(AlertDialog.Builder(this)) {
            setTitle(R.string.sign_in_failure)
            setMessage(message)
            setCancelable(true)
        }.create().show()
    }

}
