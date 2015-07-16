package com.taskworld.android.rxmovie.presentation.presenter.holder

import com.taskworld.android.model.Movie
import com.taskworld.android.rxmovie.presentation.presenter.Presenter
import com.taskworld.android.rxmovie.presentation.viewaction.holder.ItemListViewHolderViewAction
import reactiveandroid.property.PropertyOf
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 7/15/15.
 */

public class ItemListViewHolderPresenter(val model: Movie) : Presenter<ItemListViewHolderViewAction> {

    override var view: ItemListViewHolderViewAction by Delegates.notNull()

    val image: PropertyOf<String>
    val title: PropertyOf<CharSequence>

    init {
        title = PropertyOf<CharSequence>(model.title)
        image = PropertyOf("http://image.tmdb.org/t/p/w500${model.backdropPath}")
    }

    override fun onStart() {
    }

    override fun onStop() {
    }

}