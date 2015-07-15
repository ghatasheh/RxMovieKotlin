package com.taskworld.android.rxmovie.presentation.presenter.holder

import com.taskworld.android.model.Movie
import com.taskworld.android.rxmovie.presentation.presenter.Presenter
import com.taskworld.android.rxmovie.presentation.viewaction.holder.ItemListViewHolderViewAction
import reactiveandroid.property.PropertyOf
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 7/15/15.
 */

public class ItemListViewHolderPresenter(override val view: ItemListViewHolderViewAction) : Presenter<ItemListViewHolderViewAction> {

    var movie: Movie? = null
        set(value) {
            if (value == null) return
            title = PropertyOf<CharSequence>(value.title)
            image = PropertyOf("http://image.tmdb.org/t/p/w500${value.backdropPath}")
            $movie = value
        }

    var title: PropertyOf<CharSequence> by Delegates.notNull()
    var image: PropertyOf<String> by Delegates.notNull()

    override fun onStart() {
    }

    override fun onStop() {
    }

}