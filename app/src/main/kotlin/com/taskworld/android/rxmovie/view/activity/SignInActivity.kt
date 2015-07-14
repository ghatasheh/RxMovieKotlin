package com.taskworld.android.rxmovie.view.activity

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.taskworld.android.rxmovie.R
import com.taskworld.android.rxmovie.presentation.presenter.SignInPresenter
import com.taskworld.android.rxmovie.presentation.view.SignInViewAction
import com.taskworld.android.rxmovie.view.RxMovieApplication
import fuel.util.build

import widget.enabled
import widget.textChanged
import widget.visibility

import kotlinx.android.synthetic.activity_sign_in.*
import widget.text

/**
 * Created by Kittinun Vantasin on 7/10/15.
 */

class SignInActivity : AppCompatActivity(), SignInViewAction {

    val presenter = SignInPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super<AppCompatActivity>.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_in)

        bindObservables()

        signInButton.setOnClickListener {
            presenter.requestSignIn(presenter.email.value, presenter.password.value)

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0)
        }

        clearButton.setOnClickListener {
            emailEdit.setText("")
            passwordEdit.setText("")
        }

    }

    fun bindObservables() {
        presenter.email.bind(emailEdit.textChanged)
        presenter.password.bind(passwordEdit.textChanged)

        signInButton.enabled.bind(presenter.signInEnabled)
        clearButton.visibility.bind(presenter.clearVisible)

        tokenText.text.bind(presenter.token)
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
