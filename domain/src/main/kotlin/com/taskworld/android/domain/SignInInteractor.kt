package com.taskworld.android.domain

import android.util.Log
import com.google.gson.GsonBuilder
import com.taskworld.android.domain.network.TheMovieDB
import com.taskworld.android.domain.network.responseAsStringObservable
import com.taskworld.android.response.TokenResponse
import com.taskworld.android.response.ValidateLoginResponse
import fuel.Fuel
import rx.Observable
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 7/13/15.
 */

class SignInInteractor() : Interactor {

    val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss zzz").create()

    var username: String by Delegates.notNull()
    var password: String by Delegates.notNull()

    override fun invoke(): Observable<ValidateLoginResponse> = authenticateToken().concatMap {
        validateLoginWithToken(it)
    }

    fun authenticateToken(): Observable<TokenResponse> =
            Fuel.get(TheMovieDB.AUTHENTICATE_TOKEN).responseAsStringObservable().flatMap { responseString ->
                val tokenResponse = gson.fromJson(responseString, javaClass<TokenResponse>())
                Observable.just(tokenResponse)
            }

    fun validateLoginWithToken(response: TokenResponse): Observable<ValidateLoginResponse> {
        val params = mapOf("username" to username, "password" to password, "request_token" to response.token)
        return Fuel.get(TheMovieDB.VALIDATE_LOGIN, params).responseAsStringObservable().flatMap { responseString ->
            val validateResponse = gson.fromJson(responseString, javaClass<ValidateLoginResponse>())
            Observable.just(validateResponse)
        }
    }

}