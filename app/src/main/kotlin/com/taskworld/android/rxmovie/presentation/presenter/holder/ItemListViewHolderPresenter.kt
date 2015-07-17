package com.taskworld.android.rxmovie.presentation.presenter.holder

import com.taskworld.android.model.Movie
import com.taskworld.android.model.TV
import com.taskworld.android.rxmovie.presentation.presenter.Presenter
import com.taskworld.android.rxmovie.presentation.viewaction.holder.ItemListViewHolderViewAction
import reactiveandroid.property.PropertyOf
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
            override val image = tv.posterPath

        }
    }


public class ItemListViewHolderPresenter(val presentable: ItemListPresentable) : Presenter<ItemListViewHolderViewAction> {

    override var view: ItemListViewHolderViewAction by Delegates.notNull()

    val image: PropertyOf<String>
    val title: PropertyOf<CharSequence>

    init {
        title = PropertyOf<CharSequence>(presentable.title)
        image = PropertyOf("http://image.tmdb.org/t/p/w500${presentable.image}")
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

}