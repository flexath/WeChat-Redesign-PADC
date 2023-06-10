package com.flexath.moments.network.storage

import android.graphics.Bitmap
import com.flexath.moments.data.vos.MessageVO

interface RealtimeFirebaseApi {

    fun getOtp(
        onSuccess: (groceries: String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun sendMessage(senderId: String, receiverId: String,timeStamp:Long, message: MessageVO)
    fun getMessages(
        senderId: String,
        receiverId: String,
        onSuccess: (messageList: List<MessageVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun uploadAndSendImage(bitmap: Bitmap, onSuccess: (file: String) -> Unit, onFailure: (String) -> Unit)

}