package com.taskworld.android.rxmovie.presentation.presenter

/**
 * Created by Kittinun Vantasin on 7/12/15.
 */

interface Presenter<T> {

    val view: T

    fun onCreate() {}

    fun onStart()
    fun onStop()

    fun onDestroy() {}

}