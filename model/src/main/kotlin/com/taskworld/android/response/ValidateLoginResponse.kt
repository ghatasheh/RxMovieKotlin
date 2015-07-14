package com.taskworld.android.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Kittinun Vantasin on 7/14/15.
 */

data class ValidateLoginResponse : Response {

    SerializedName("success")
    override val isSuccessful: Boolean = false

    SerializedName("request_token")
    val requestToken: String = ""

}