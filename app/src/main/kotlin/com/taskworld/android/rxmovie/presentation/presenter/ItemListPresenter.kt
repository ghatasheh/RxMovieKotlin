package com.taskworld.android.rxmovie.presentation.presenter

import android.util.Log
import com.taskworld.android.domain.ItemListInteractor
import com.taskworld.android.domain.MovieListInteractor
import com.taskworld.android.domain.TVListInteractor
import com.taskworld.android.model.Movie
import com.taskworld.android.model.TV
import com.taskworld.android.rxmovie.presentation.presenter.base.ReactivePresenter
import com.taskworld.android.rxmovie.presentation.presenter.holder.ItemListPresentable
import com.taskworld.android.rxmovie.presentation.presenter.holder.ItemListViewHolderPresenter
import com.taskworld.android.rxmovie.presentation.presenter.holder.itemListPresentable
import com.taskworld.android.rxmovie.presentation.viewaction.ItemListViewAction
import com.taskworld.android.rxmovie.util.TAG
import com.taskworld.android.rxmovie.view.fragment.ItemListFragment
import reactiveandroid.property.MutablePropertyOf
import reactiveandroid.rx.liftObservable
import reactiveandroid.rx.plusAssign
import reactiveandroid.scheduler.AndroidSchedulers
import rx.Observable
import rx.subscriptions.CompositeSubscription
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 7/15/15.
 */

class ItemListPresenter(override var view: ItemListViewAction) : ReactivePresenter(), ListPresenter<ItemListViewAction> {

    //interactor
    var interactor: ItemListInteractor<*> by Delegates.notNull()

    var type: ItemListFragment.Type by Delegates.notNull()

    //data
    override val itemCount = MutablePropertyOf(0)
    val items = MutablePropertyOf(arrayListOf<ItemListViewHolderPresenter>())

    var isLoading = false
    var pageNumber = 1

    val subscriptions = CompositeSubscription()

    init {
        becomeActive.subscribe {
            subscriptions += liftObservable(listViewHolderObservable(), ::updateItems)
        }

        becomeInactive.subscribe {
            subscriptions.unsubscribe()
        }
    }

    fun loadMore() {
        if (isLoading) return

        isLoading = true

        pageNumber++
        subscriptions += liftObservable(listViewHolderObservable(), ::updateItems)
    }

    fun listViewHolderObservable(): Observable<List<ItemListViewHolderPresenter>> {
        var construct: ((Any) -> ItemListPresentable)
        when (type) {
            ItemListFragment.Type.Movie -> {
                interactor = MovieListInteractor()
                interactor.page = pageNumber
                construct = { (it as Movie).itemListPresentable }
            }
            ItemListFragment.Type.TV -> {
                interactor = TVListInteractor()
                interactor.page = pageNumber
                construct = { (it as TV).itemListPresentable }
            }
        }

        return interactor.invoke().observeOn(AndroidSchedulers.mainThreadScheduler()).map { list ->
            list.map { ItemListViewHolderPresenter(construct.invoke(it!!)) }
        }
    }

    fun updateItems(presenters: List<ItemListViewHolderPresenter>) {
        isLoading = false
        items.value.addAll(presenters)
        itemCount.value = items.value.size()
    }

    fun get(position: Int) = items.value[position]

}