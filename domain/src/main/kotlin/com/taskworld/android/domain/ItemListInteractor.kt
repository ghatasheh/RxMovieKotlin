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

interface ItemListInteractor<T> : Interactor<List<T>> {

    var page: Int

    override fun invoke(): Observable<List<T>>

}

class MovieListInteractor : ItemListInteractor<Movie> {

    override var page: Int = 1

    override fun invoke(): Observable<List<Movie>> {
        return Fuel.get(TheMovieDB.DISCOVER_MOVIE, mapOf("page" to page)).responseAsStringObservable().map {
            val response = GsonBuilder().create().fromJson(it, javaClass<DiscoverMovieResponse>())
            response.items
        }
    }

}
class TVListInteractor : ItemListInteractor<TV> {

    override var page: Int = 1

    override fun invoke(): Observable<List<TV>> {
        return Fuel.get(TheMovieDB.DISCOVER_TV, mapOf("page" to page)).responseAsStringObservable().map {
            val response = GsonBuilder().create().fromJson(it, javaClass<DiscoverTVResponse>())
            response.items
        }
    }

}
