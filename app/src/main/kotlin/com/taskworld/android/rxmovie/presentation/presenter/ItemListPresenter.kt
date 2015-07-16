package com.taskworld.android.rxmovie.presentation.presenter

import com.taskworld.android.domain.MovieListInteractor
import com.taskworld.android.model.Movie
import com.taskworld.android.rxmovie.presentation.presenter.holder.ItemListViewHolderPresenter
import com.taskworld.android.rxmovie.presentation.viewaction.ItemListViewAction
import fuel.util.build
import reactiveandroid.property.MutablePropertyOf
import reactiveandroid.util.liftObservable
import rx.Observable

/**
 * Created by Kittinun Vantasin on 7/15/15.
 */

class ItemListPresenter(override var view: ItemListViewAction) : Presenter<ItemListViewAction> {

    //interactor
    val interactor = MovieListInteractor()

    //data
    val itemCount = MutablePropertyOf(0)
    val items = MutablePropertyOf(arrayListOf<ItemListViewHolderPresenter>())

    var isLoading = false
    var pageNumber = 1

    override fun onStart() {
        liftObservable(listViewHolderObservable(), ::updateItems)
    }

    override fun onStop() {
    }

    fun loadMore() {
        if (isLoading) return

        build(interactor) {
            page = (pageNumber + 1)
        }

        liftObservable(listViewHolderObservable(), ::updateItems)
    }

    fun listViewHolderObservable(): Observable<List<ItemListViewHolderPresenter>> {
        return interactor.invoke().map { list ->
            list.map { ItemListViewHolderPresenter(it) }
        }
    }

    fun updateItems(presenters: List<ItemListViewHolderPresenter>) {
        items.value.addAll(presenters)
        itemCount.value = items.value.size()
    }

    fun get(position: Int) = items.value[position]

}