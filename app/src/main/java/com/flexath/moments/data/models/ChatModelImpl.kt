package com.flexath.moments.data.models

import com.flexath.moments.data.vos.MessageVO
import com.flexath.moments.network.storage.RealtimeDatabaseFirebaseApiImpl
import com.flexath.moments.network.storage.RealtimeFirebaseApi

object ChatModelImpl : ChatModel {

    override var mFirebaseApi: RealtimeFirebaseApi = RealtimeDatabaseFirebaseApiImpl

    override fun getOtp(onSuccess: (groceries: String) -> Unit, onFailure: (String) -> Unit) {
        mFirebaseApi.getOtp(onSuccess, onFailure)
    }


    override fun sendMessage(
        senderId: String,
        receiverId: String,
        timeStamp: Long,
        message: MessageVO
    ) {
        mFirebaseApi.sendMessage(senderId, receiverId, timeStamp, message)
    }

    override fun getMessages(
        senderId: String,
        receiverId: String,
        onSuccess: (groceries: List<MessageVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getMessages(senderId, receiverId, onSuccess, onFailure)
    }

}