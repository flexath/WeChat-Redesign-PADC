package com.flexath.moments.network.retrofit.responses

import com.flexath.moments.data.vos.fcm.ResultVO
import com.google.gson.annotations.SerializedName

data class FCMResponse(

    @SerializedName("canonical_ids")
    val canonicalIds: Int?,

    @SerializedName("failure")
    val failure: Int?,

    @SerializedName("multicast_id")
    val multicastId: Long?,

    @SerializedName("results")
    val results: List<ResultVO>?,

    @SerializedName("success")
    val success: Int?
)