package com.taskworld.android.rxmovie.view.activity

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.taskworld.android.rxmovie.R
import com.taskworld.android.rxmovie.presentation.presenter.SignInPresenter
import com.taskworld.android.rxmovie.presentation.view.SignInViewAction
import com.taskworld.android.rxmovie.util.TAG
import com.taskworld.android.rxmovie.view.RxMovieApplication
import fuel.util.build
import kotlinx.android.synthetic.activity_sign_in.clearButton
import kotlinx.android.synthetic.activity_sign_in.emailEdit
import kotlinx.android.synthetic.activity_sign_in.passwordEdit
import kotlinx.android.synthetic.activity_sign_in.signInButton
import rx.subscriptions.CompositeSubscription
import widget.enabled
import widget.textChanged
import widget.visibility

/**
 * Created by Kittinun Vantasin on 7/10/15.
 */

class SignInActivity : AppCompatActivity(), SignInViewAction {

    val presenter = SignInPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super<AppCompatActivity>.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_in)

        presenter.email.bind(emailEdit.textChanged)
        presenter.password.bind(passwordEdit.textChanged)

        signInButton.enabled.bind(presenter.signInEnabled)
        clearButton.visibility.bind(presenter.clearVisible)

        clearButton.setOnClickListener {
            emailEdit.setText("")
            passwordEdit.setText("")
        }

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
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT)
    }

    override fun showSignInFailure(message: String) {
        build(AlertDialog.Builder(this)) {
            setTitle("Error")
            setMessage(message)
        }
    }

}
