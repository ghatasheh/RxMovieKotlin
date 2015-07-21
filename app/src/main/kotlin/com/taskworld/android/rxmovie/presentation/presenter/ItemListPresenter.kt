package com.taskworld.android.rxmovie.presentation.presenter

import android.util.Log
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
import reactiveandroid.rx.Action
import reactiveandroid.rx.liftWith
import reactiveandroid.rx.plusAssign
import reactiveandroid.scheduler.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 7/15/15.
 */

class ItemListPresenter(override var view: ItemListViewAction) : ReactivePresenter(), ListPresenter<ItemListViewAction> {

    var type: ItemListFragment.Type? = null
        set(value) {
            val value = value!!
            loadAction = loadListViewHolderAction(value)
            loadAction.name = "loadAction"
            loadMoreAction = loadListViewHolderAction(value)
            loadMoreAction.name = "loadMoreAction"
            $type = value
        }

    //data
    override val itemCount = MutablePropertyOf(0)
    val items = MutablePropertyOf(arrayListOf<ItemListViewHolderPresenter>())

    var pageNumber = 1

    val subscriptions = CompositeSubscription()

    var loadAction: Action<Int, List<ItemListViewHolderPresenter>> by Delegates.notNull()
    var loadMoreAction: Action<Int, List<ItemListViewHolderPresenter>> by Delegates.notNull()

    init {
        becomeActive.subscribe {
            subscriptions += loadAction.execute(pageNumber).observeOn(AndroidSchedulers.mainThreadScheduler())
                    .liftWith(this, ::updateItems, ::handleError)
        }

        becomeInactive.subscribe {
            subscriptions.unsubscribe()
        }
    }

    fun loadMore() {
        subscriptions += loadMoreAction.execute(++pageNumber).observeOn(AndroidSchedulers.mainThreadScheduler())
                .liftWith(this, ::updateItems, ::handleError)
    }

    fun loadListViewHolderAction(t: ItemListFragment.Type): Action<Int, List<ItemListViewHolderPresenter>> {
        val interactor = when (t) {
            ItemListFragment.Type.Movie -> MovieListInteractor()
            ItemListFragment.Type.TV -> TVListInteractor()
            else -> MovieListInteractor()
        }

        val action = Action { number: Int ->
            interactor.page = number
            interactor.invoke().map { list ->
                list.map {
                    ItemListViewHolderPresenter(constructItemListPresentable(it))
                }
            }
        }

        return action
    }

    fun constructItemListPresentable(model: Any): ItemListPresentable {
        return when (model) {
            is TV -> model.itemListPresentable
            is Movie -> model.itemListPresentable
            else -> throw RuntimeException()
        }
    }

    fun updateItems(presenters: List<ItemListViewHolderPresenter>) {
        items.value.addAll(presenters)
        itemCount.value = items.value.size()
    }

    fun handleError(e: Throwable?) {
        Log.e(TAG, e?.getMessage() ?: "error")
    }

    fun get(position: Int) = items.value[position]

}