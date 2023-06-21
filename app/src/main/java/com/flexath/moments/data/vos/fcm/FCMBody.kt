package com.flexath.moments.data.vos.fcm

import com.google.gson.annotations.SerializedName

data class FCMBody(

    @SerializedName("registration_ids")
    val registration_ids: List<String>?,

    @SerializedName("data")
    val data: Data?
)