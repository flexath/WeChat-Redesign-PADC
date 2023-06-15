package com.flexath.moments.data.models

import android.content.Context
import android.graphics.Bitmap
import com.flexath.moments.data.vos.GroupVO
import com.flexath.moments.data.vos.PrivateMessageVO
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
        message: PrivateMessageVO
    ) {
        mFirebaseApi.sendMessage(senderId, receiverId, timeStamp, message)
    }

    override fun getMessages(
        senderId: String,
        receiverId: String,
        onSuccess: (groceries: List<PrivateMessageVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getMessages(senderId, receiverId, onSuccess, onFailure)
    }

    override fun uploadAndSendImage(
        bitmap: Bitmap,
        onSuccess: (file: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.uploadAndSendImage(bitmap,onSuccess,onFailure)
    }

    override fun uploadGif(
        gifString: String,
        context: Context,
        onSuccess: (file: String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.uploadGif(gifString, context, onSuccess, onFailure)
    }

    override fun getChatHistoryUserId(
        senderId: String,
        onSuccess: (messageList: List<String>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getChatHistoryUserId(senderId, onSuccess, onFailure)
    }

    override fun addGroup(
        timeStamp: Long,
        groupName: String,
        userList: List<String>
    ) {
        mFirebaseApi.addGroup(timeStamp, groupName, userList)
    }

    override fun getGroups(
        onSuccess: (groupIdList: List<GroupVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getGroups(onSuccess,onFailure)
    }

    override fun sendGroupMessage(groupId: Long, timeStamp:Long, message: PrivateMessageVO) {
        mFirebaseApi.sendGroupMessage(groupId,timeStamp, message)
    }

    override fun getGroupMessages(
        groupId: Long,
        onSuccess: (messageList: List<PrivateMessageVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getGroupMessages(groupId, onSuccess, onFailure)
    }
}