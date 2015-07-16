package com.taskworld.android.domain

import rx.Observable

/**
 * Created by Kittinun Vantasin on 7/13/15.
 */

interface Interactor<T> {

    fun invoke(): Observable<T>

}