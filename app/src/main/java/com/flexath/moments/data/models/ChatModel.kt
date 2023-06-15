package com.flexath.moments.data.models

import android.content.Context
import android.graphics.Bitmap
import com.flexath.moments.data.vos.GroupVO
import com.flexath.moments.data.vos.PrivateMessageVO
import com.flexath.moments.network.storage.RealtimeFirebaseApi

interface ChatModel {

    var mFirebaseApi:RealtimeFirebaseApi

    fun getOtp(
        onSuccess: (groceries: String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun sendMessage(senderId: String, receiverId: String,timeStamp:Long, message: PrivateMessageVO)

    fun getMessages(
        senderId: String,
        receiverId: String,
        onSuccess: (groceries: List<PrivateMessageVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun uploadAndSendImage(bitmap: Bitmap, onSuccess: (file: String) -> Unit, onFailure: (String) -> Unit)

    fun uploadGif(
        gifString: String,
        context: Context,
        onSuccess: (file: String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getChatHistoryUserId(
        senderId: String,
        onSuccess: (messageList: List<String>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun addGroup(timeStamp: Long, groupName: String,userList:List<String>)

    fun getGroups(
        onSuccess: (groupIdList: List<GroupVO>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun sendGroupMessage(groupId: Long,timeStamp:Long, message:PrivateMessageVO)

    fun getGroupMessages(
        groupId:Long,
        onSuccess: (messageList: List<PrivateMessageVO>) -> Unit,
        onFailure: (String) -> Unit
    )
}