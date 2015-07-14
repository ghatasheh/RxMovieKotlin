package com.taskworld.android.domain.network

import fuel.Fuel

/**
 * Created by Kittinun Vantasin on 7/8/15.
 */

enum class TheMovieDB(val relativePath: String) : Fuel.PathStringConvertible {

    DISCOVER_MOVIE("discover/movie"),
    DISCOVER_TV("discover/tv"),

    AUTHENTICATE_TOKEN("authentication/token/new");

    override val path = "https://api.themoviedb.org/3/$relativePath"

}
