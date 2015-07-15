package com.taskworld.android.domain

import com.google.gson.GsonBuilder
import com.taskworld.android.domain.network.TheMovieDB
import com.taskworld.android.domain.network.responseAsStringObservable
import com.taskworld.android.model.Movie
import com.taskworld.android.model.TV
import com.taskworld.android.response.DiscoverMovieResponse
import com.taskworld.android.response.DiscoverTVResponse
import fuel.Fuel
import rx.Observable

/**
 * Created by Kittinun Vantasin on 7/15/15.
 */

abstract class ItemListInteractor<T> : Interactor {

    abstract override fun invoke(): Observable<List<T>>
}

class MovieListInteractor : ItemListInteractor<Movie>() {

    override fun invoke(): Observable<List<Movie>> {
        return Fuel.get(TheMovieDB.DISCOVER_MOVIE).responseAsStringObservable().map {
            val response = GsonBuilder().create().fromJson(it, javaClass<DiscoverMovieResponse>())
            response.movies
        }
    }

}
class TVListInteractor : ItemListInteractor<TV>() {

    override fun invoke(): Observable<List<TV>> {
        return Fuel.get(TheMovieDB.DISCOVER_TV).responseAsStringObservable().map {
            val response = GsonBuilder().create().fromJson(it, javaClass<DiscoverTVResponse>())
            arrayListOf(TV())
        }
    }

}
