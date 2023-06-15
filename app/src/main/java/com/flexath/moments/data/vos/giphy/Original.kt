package com.flexath.moments.data.vos.giphy

import com.google.gson.annotations.SerializedName

data class Original(
    @SerializedName("url")
    val url: String?
)