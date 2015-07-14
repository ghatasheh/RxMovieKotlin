package com.taskworld.android.domain

import rx.Observable

/**
 * Created by Kittinun Vantasin on 7/13/15.
 */

interface Interactor {

    fun invoke(): Observable<*>

}