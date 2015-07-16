package com.taskworld.android.response

import com.google.gson.annotations.SerializedName
import com.taskworld.android.model.TV

/**
 * Created by Kittinun Vantasin on 7/15/15.
 */

data class DiscoverTVResponse(

        override val isSuccessful: Boolean = true,

        SerializedName("results")
        override val items: List<TV> = arrayListOf()

) : ListResponse<TV>