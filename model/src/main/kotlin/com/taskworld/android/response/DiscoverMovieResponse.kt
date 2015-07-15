package com.taskworld.android.response

import com.google.gson.annotations.SerializedName
import com.taskworld.android.model.Movie

/**
 * Created by Kittinun Vantasin on 7/15/15.
 */

data class DiscoverMovieResponse : Response {

    override val isSuccessful: Boolean = true

    SerializedName("results")
    val movies: List<Movie> = arrayListOf()

}