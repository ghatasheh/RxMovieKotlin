package com.taskworld.android.rxmovie.presentation.presenter

import android.util.Log
import android.view.View
import com.taskworld.android.domain.SignInInteractor
import com.taskworld.android.rxmovie.presentation.view.SignInViewAction
import com.taskworld.android.rxmovie.util.TAG
import fuel.util.build
import property.MutablePropertyOf
import rx.Observable
import util.AndroidSchedulers

/**
 * Created by Kittinun Vantasin on 7/12/15.
 */

class SignInPresenter(override val view: SignInViewAction) : Presenter<SignInViewAction> {

    val email = MutablePropertyOf<CharSequence>("")
    val password = MutablePropertyOf<CharSequence>("")

    val token = MutablePropertyOf<CharSequence>("")

    val signInEnabled = Observable.combineLatest(email.observable, password.observable) { e, p ->
        isValidEmailPattern(e) && isValidPassword(p)
    }

    val clearVisible = Observable.combineLatest(email.observable, password.observable) { e, p ->
        if (e.length() == 0 && p.length() == 0) View.GONE else View.VISIBLE
    }

    val interactor = SignInInteractor()

    override fun onStart() {

    }

    override fun onStop() {

    }

    fun requestSignIn(user: CharSequence, pass: CharSequence) {
        build(interactor) {
            username = user.toString()
            password = pass.toString()
        }.invoke().observeOn(AndroidSchedulers.mainThreadScheduler()).subscribe({ response ->
            view.showSignInSuccess()
            token.value = response.requestToken
        }, { error ->
            view.showSignInFailure("username or password is not valid")
        })
    }

    fun isValidEmailPattern(text: CharSequence) =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
                    .toRegex()
                    .matches(text) or text.toString().equals("kittinunf");

    fun isValidPassword(password: CharSequence) = password.length() >= 6

}