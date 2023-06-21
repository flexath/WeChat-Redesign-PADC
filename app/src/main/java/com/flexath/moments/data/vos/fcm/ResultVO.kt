package com.flexath.moments.data.vos.fcm

import com.google.gson.annotations.SerializedName

data class ResultVO(

    @SerializedName("message_id")
    val messageId: String?
)