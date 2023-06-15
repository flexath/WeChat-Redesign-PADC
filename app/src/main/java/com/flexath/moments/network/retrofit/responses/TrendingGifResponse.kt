package com.flexath.moments.network.retrofit.responses

import com.flexath.moments.data.vos.giphy.Data
import com.google.gson.annotations.SerializedName

data class TrendingGifResponse(
    @SerializedName("data")
    val data: List<Data>?
)