package com.taskworld.android.rxmovie.presentation.presenter.holder

import android.view.View
import com.taskworld.android.model.Movie
import com.taskworld.android.model.TV
import com.taskworld.android.rxmovie.presentation.presenter.Presenter
import com.taskworld.android.rxmovie.presentation.presenter.base.ReactivePresenter
import com.taskworld.android.rxmovie.presentation.viewaction.holder.ItemListViewHolderViewAction
import reactiveandroid.property.PropertyOf
import rx.Observable
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 7/15/15.
 */

interface ItemListPresentable {

    val title: String
    val image: String

}

val Movie.itemListPresentable: ItemListPresentable
    get () {
        val movie = this
        return object : ItemListPresentable {

            override val title = movie.title
            override val image = movie.backdropPath

        }
    }

val TV.itemListPresentable: ItemListPresentable
    get () {
        val tv = this
        return object : ItemListPresentable {

            override val title = tv.name
            override val image = tv.backdropPath

        }
    }


public class ItemListViewHolderPresenter(val presentable: ItemListPresentable) : ReactivePresenter(), Presenter<ItemListViewHolderViewAction> {

    override var view: ItemListViewHolderViewAction by Delegates.notNull()

    val image: PropertyOf<String>
    val title: PropertyOf<CharSequence>

    var click: Observable<View> by Delegates.notNull()

    init {
        title = PropertyOf<CharSequence>(presentable.title)
        image = PropertyOf("http://image.tmdb.org/t/p/w500${presentable.image}")

        becomeActive.subscribe {
            click.subscribe { view.navigateToItemDetail(presentable.title) }
        }

    }

}

