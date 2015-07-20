package com.taskworld.android.rxmovie.presentation.presenter

import android.util.Log
import android.view.View
import com.taskworld.android.domain.SignInInteractor
import com.taskworld.android.response.ValidateLoginResponse
import com.taskworld.android.rxmovie.presentation.presenter.base.ReactivePresenter
import com.taskworld.android.rxmovie.presentation.viewaction.SignInViewAction
import com.taskworld.android.rxmovie.util.TAG
import reactiveandroid.property.MutablePropertyOf
import reactiveandroid.rx.Action
import reactiveandroid.scheduler.AndroidSchedulers
import rx.Notification
import rx.Observable

/**
 * Created by Kittinun Vantasin on 7/12/15.
 */

class SignInPresenter(override var view: SignInViewAction) : ReactivePresenter(), Presenter<SignInViewAction> {

    //interactor
    val interactor = SignInInteractor()

    val email = MutablePropertyOf<CharSequence>("")
    val pass = MutablePropertyOf<CharSequence>("")

    val token = MutablePropertyOf<CharSequence>("")

    val clearVisible = Observable.combineLatest(email.observable, pass.observable) { e, p ->
        if (e.length() == 0 && p.length() == 0) View.GONE else View.VISIBLE
    }

    val validateSignIn: MutablePropertyOf<Boolean> = MutablePropertyOf(false)

    fun requestSignInAction(): Action<Pair<String, String>, ValidateLoginResponse> {
        validateSignIn.bind(isValidSignIn())

        val action = Action(validateSignIn) { p: Pair<String, String> ->
            val (user, pass) = p
            interactor.username = user
            interactor.password = pass
            interactor.invoke()
        }

        action.executions.subscribe { o ->
            o.materialize().filter {
                it.getKind().equals(Notification.Kind.OnNext) or it.getKind().equals(Notification.Kind.OnError)
            }.observeOn(AndroidSchedulers.mainThreadScheduler()).subscribe {
                when (it.getKind()) {
                    Notification.Kind.OnNext -> {
                        view.showSignInSuccess()
                        token.value = it.getValue().requestToken
                    }
                    Notification.Kind.OnError -> view.showSignInFailure("Username or Password is incorrect")
                }
            }
        }

        return action
    }

    fun isValidSignIn() = Observable.combineLatest(email.observable, pass.observable) { e, p ->
        isValidEmailPattern(e) && isValidPassword(p)
    }

    fun isValidEmailPattern(text: CharSequence) =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
                    .toRegex()
                    .matches(text) or text.toString().equals("kittinunf");

    fun isValidPassword(password: CharSequence) = password.length() >= 6

}