package com.taskworld.android.rxmovie.presentation.presenter.base

import rx.subjects.PublishSubject
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 7/17/15.
 */

open class ReactivePresenter {

    var active by Delegates.observable(false) { des, oldValue, newValue ->
        activeSubject.onNext(newValue)
    }

    private val activeSubject = PublishSubject.create<Boolean>()

    val becomeActive = activeSubject.filter { it }.map { Unit }
    val becomeInactive = activeSubject.filter { !it }.map { Unit }

}