package com.taskworld.android.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Kittinun Vantasin on 7/8/15.
 */

data class Movie(
        SerializedName("backdrop_path")
        val backdropPath: String,
        val overview: String,
        val popularity: Float,
        SerializedName("poster_path")
        val posterPath: String,
        val title: String
) : BaseModel()
