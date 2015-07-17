package com.taskworld.android.rxmovie.presentation.presenter

import reactiveandroid.property.MutablePropertyOf

/**
 * Created by Kittinun Vantasin on 7/12/15.
 */

interface Presenter<T> {

    var view: T

}

interface ListPresenter<T> : Presenter<T> {

    val itemCount: MutablePropertyOf<Int>

}