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
import com.taskworld.android.rxmovie.presentation.view.SignInViewAction
import com.taskworld.android.rxmovie.util.TAG
import com.taskworld.android.rxmovie.view.RxMovieApplication
import fuel.util.build
import kotlinx.android.synthetic.activity_sign_in.*
import rx.Observable
import util.liftObservable
import view.click
import view.enabled
import view.focusChange
import view.visibility
import widget.*
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 7/10/15.
 */

class SignInActivity : AppCompatActivity(), SignInViewAction {

    val presenter = SignInPresenter(this)

    val signInButtonEnabled by Delegates.lazy { signInButton.enabled }
    val clearButtonVisibility by Delegates.lazy { clearButton.visibility }

    override fun onCreate(savedInstanceState: Bundle?) {
        super<AppCompatActivity>.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_in)

        bindObservables()

        liftObservable(signInButton.click, ::handleSignInButtonClicked)
        liftObservable(clearButton.click, ::handleClearButtonClicked)
        liftObservable(Observable.merge(emailEdit.focusChange, passwordEdit.focusChange), ::checkFocus)
    }

    fun bindObservables() {
        presenter.email.bind(emailEdit.textChanged)
        presenter.pass.bind(passwordEdit.textChanged)

        signInButtonEnabled.bind(presenter.signInEnabled)
        clearButtonVisibility.bind(presenter.clearVisible)

        tokenText.text.bind(presenter.token)
    }

    fun handleSignInButtonClicked(_: View) {
        presenter.requestSignIn()

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0)
    }

    fun handleClearButtonClicked(_: View) {
        emailEdit.setText("")
        passwordEdit.setText("")
    }

    fun checkFocus(pair: Pair<View, Boolean>) {
        val (view, focus) = pair
        Log.e(TAG, "view: $view, focus: $focus")
    }

    override fun onStart() {
        super<AppCompatActivity>.onStart()

        presenter.onStart()
    }

    override fun onStop() {
        super<AppCompatActivity>.onStop()

        presenter.onStop()
    }

    override fun onDestroy() {
        super<AppCompatActivity>.onDestroy()

        val watcher = RxMovieApplication.refWatcher(this)
        watcher.watch(this)
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
        }.create().show()
    }

}
