package com.taskworld.android.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Kittinun Vantasin on 7/15/15.
 */

data class TV(
        SerializedName("backdrop_path")
        val backdropPath: String,
        SerializedName("poster_path")
        val posterPath: String,
        SerializedName("first_air_date")
        val firstAirDate: String,
        SerializedName("original_name")
        val name: String,
        SerializedName("popularity")
        val popularity: Float,
        val overview: String
) : BaseModel()