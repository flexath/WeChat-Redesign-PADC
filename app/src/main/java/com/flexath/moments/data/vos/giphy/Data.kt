package com.flexath.moments.data.vos.giphy

import com.google.gson.annotations.SerializedName

data class Data(

    @SerializedName("id")
    val id: String?,

    @SerializedName("images")
    val images: Images?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("type")
    val type: String?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("username")
    val username: String?
)