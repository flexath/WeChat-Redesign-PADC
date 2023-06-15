package com.flexath.moments.data.vos.giphy

import com.google.gson.annotations.SerializedName

data class Images(

    @SerializedName("original")
    val original: Original?
)