package com.flexath.moments.mvp.interfaces

import android.graphics.Bitmap
import com.flexath.moments.data.vos.MessageVO
import com.flexath.moments.mvp.views.ChatDetailView

interface ChatDetailPresenter : BasePresenter<ChatDetailView> {

    fun onTapGetImageButton()

    fun onTapOpenCameraButton()

    fun getUserId(): String
    fun sendMessage(senderId: String, receiverId: String, timeStamp: Long, message: MessageVO)

    fun getMessages(
        senderId: String,
        receiverId: String
    )

    fun uploadAndSendImage(bitmap: Bitmap)

    fun sendGroupMessage(groupId: Long, timeStamp:Long, message:MessageVO)

    fun getGroupMessages(
        groupId:Long
    )
}