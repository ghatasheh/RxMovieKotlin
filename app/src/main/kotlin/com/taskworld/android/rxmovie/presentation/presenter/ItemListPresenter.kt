package com.taskworld.android.rxmovie.presentation.presenter

import com.taskworld.android.domain.ItemListInteractor
import com.taskworld.android.domain.MovieListInteractor
import com.taskworld.android.domain.TVListInteractor
import com.taskworld.android.model.Movie
import com.taskworld.android.model.TV
import com.taskworld.android.rxmovie.presentation.presenter.holder.ItemListPresentable
import com.taskworld.android.rxmovie.presentation.presenter.holder.ItemListViewHolderPresenter
import com.taskworld.android.rxmovie.presentation.presenter.holder.itemListPresentable
import com.taskworld.android.rxmovie.presentation.viewaction.ItemListViewAction
import com.taskworld.android.rxmovie.view.fragment.ItemListFragment
import fuel.util.build
import reactiveandroid.property.MutablePropertyOf
import reactiveandroid.rx.liftObservable
import reactiveandroid.scheduler.AndroidSchedulers
import rx.Observable
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 7/15/15.
 */

class ItemListPresenter(override var view: ItemListViewAction) : Presenter<ItemListViewAction> {

    //interactor
    var interactor: ItemListInteractor<*> by Delegates.notNull()

    var type: ItemListFragment.Type by Delegates.notNull()

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
        var construct: ((Any) -> ItemListPresentable)
        when (type) {
            ItemListFragment.Type.Movie -> {
                interactor = MovieListInteractor()
                construct = { (it as Movie).itemListPresentable }
            }
            ItemListFragment.Type.TV -> {
                interactor = TVListInteractor()
                construct = { (it as TV).itemListPresentable }
            }
        }

        return interactor.invoke().observeOn(AndroidSchedulers.mainThreadScheduler()).map { list ->
            list.map { ItemListViewHolderPresenter(construct.invoke(it!!)) }
        }
    }

    fun updateItems(presenters: List<ItemListViewHolderPresenter>) {
        items.value.addAll(presenters)
        itemCount.value = items.value.size()
    }

    fun get(position: Int) = items.value[position]

}