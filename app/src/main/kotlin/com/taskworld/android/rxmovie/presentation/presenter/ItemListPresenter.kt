package com.taskworld.android.rxmovie.presentation.presenter

import android.util.Log
import com.taskworld.android.domain.MovieListInteractor
import com.taskworld.android.model.Movie
import com.taskworld.android.rxmovie.presentation.viewaction.ItemListViewAction
import com.taskworld.android.rxmovie.util.TAG
import reactiveandroid.property.MutablePropertyOf
import reactiveandroid.scheduler.AndroidSchedulers
import reactiveandroid.util.liftObservable

/**
 * Created by Kittinun Vantasin on 7/15/15.
 */

class ItemListPresenter(override val view: ItemListViewAction) : Presenter<ItemListViewAction> {

    val itemCount = MutablePropertyOf(0)

    val items = MutablePropertyOf(listOf<Movie>())

    val interactor = MovieListInteractor()

    override fun onStart() {
        val observable = interactor.invoke().observeOn(AndroidSchedulers.mainThreadScheduler())
        liftObservable(observable, ::setItems)
    }

    override fun onStop() {
    }

    fun setItems(movies: List<Movie>) {
        items.value = movies
        itemCount.value = movies.size()
    }

    fun get(position: Int) = items.value[position]

}