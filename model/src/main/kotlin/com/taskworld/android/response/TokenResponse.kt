package com.taskworld.android.response

import com.google.gson.annotations.SerializedName
import java.util.Date
import kotlin.properties.Delegates

/**
 * Created by Kittinun Vantasin on 7/14/15.
 */

data class TokenResponse : Response {

    SerializedName("success")
    override val isSuccessful: Boolean = false

    SerializedName("request_token")
    val token: String = ""

    SerializedName("expires_at")
    val expiryDate: Date = Date(0)

}