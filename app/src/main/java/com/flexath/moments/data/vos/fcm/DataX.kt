package com.flexath.moments.data.vos.fcm

import com.google.gson.annotations.SerializedName

data class DataX(

    @SerializedName("noti_type")
    val noti_type: String? = "challenge"
)