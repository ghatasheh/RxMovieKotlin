package com.taskworld.android.rxmovie.presentation.presenter

import reactiveandroid.property.MutablePropertyOf

/**
 * Created by Kittinun Vantasin on 7/12/15.
 */

interface Presenter<T> {

    var view: T

    fun onCreate() {}

    fun onStart()
    fun onStop()

    fun onDestroy() {}

}

interface ListPresenter<T> : Presenter<T> {

    val itemCount: MutablePropertyOf<Int>

}