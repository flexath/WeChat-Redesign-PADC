package com.flexath.moments.data.vos.fcm

import com.google.gson.annotations.SerializedName

data class Data(

    @SerializedName("title")
    val title: String?,

    @SerializedName("body")
    val body: String?,

    @SerializedName("chat_type")
    val chat_type: String?,

    @SerializedName("chat_id")
    val chat_id:String?,

    @SerializedName("content_available")
    val content_available: Boolean = true,

    @SerializedName("data")
    val data: DataX,

    @SerializedName("priority")
    val priority: String = "high",

    @SerializedName("type")
    val type: String = "2"
)