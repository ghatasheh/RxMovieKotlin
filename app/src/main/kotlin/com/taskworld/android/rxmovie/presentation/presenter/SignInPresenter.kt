package com.taskworld.android.rxmovie.presentation.presenter

import android.util.Log
import android.view.View
import com.taskworld.android.domain.SignInInteractor
import com.taskworld.android.domain.network.TheMovieDB
import com.taskworld.android.rxmovie.presentation.view.SignInViewAction
import com.taskworld.android.rxmovie.util.TAG
import fuel.Fuel
import property.MutablePropertyOf
import rx.Observable

/**
 * Created by Kittinun Vantasin on 7/12/15.
 */

class SignInPresenter(override val view: SignInViewAction) : Presenter<SignInViewAction> {

    val email = MutablePropertyOf<CharSequence>("")
    val password = MutablePropertyOf<CharSequence>("")

    val signInEnabled = Observable.combineLatest(email.observable, password.observable) { e, p ->
        isValidEmailPattern(e) && isValidPassword(p)
    }

    val clearVisible = Observable.combineLatest(email.observable, password.observable) { e, p ->
        if (e.length() == 0 && p.length() == 0) View.GONE else View.VISIBLE
    }

    val interactor = SignInInteractor()

    override fun onStart() {
        interactor.invoke().subscribe({ response ->
            Log.d(TAG, response)
        }, { error ->
            Log.e(TAG, error.toString())
        })
    }

    override fun onStop() {

    }

    fun isValidEmailPattern(email: CharSequence) =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
                    .toRegex()
                    .matches(email);

    fun isValidPassword(password: CharSequence) = password.length() >= 6

}