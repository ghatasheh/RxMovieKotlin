package com.taskworld.android.domain

import com.taskworld.android.domain.network.TheMovieDB
import com.taskworld.android.domain.network.responseAsStringObservable
import fuel.Fuel
import fuel.core.Manager
import rx.Observable

/**
 * Created by Kittinun Vantasin on 7/13/15.
 */

class SignInInteractor() : Interactor {

    override fun invoke(): Observable<String> {
        return Fuel.get(TheMovieDB.AUTHENTICATE_TOKEN).responseAsStringObservable()
    }

}