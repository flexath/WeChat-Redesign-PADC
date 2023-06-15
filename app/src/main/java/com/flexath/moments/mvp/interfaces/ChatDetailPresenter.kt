package com.flexath.moments.mvp.interfaces

import android.content.Context
import android.graphics.Bitmap
import com.flexath.moments.data.vos.PrivateMessageVO
import com.flexath.moments.mvp.views.ChatDetailView

interface ChatDetailPresenter : BasePresenter<ChatDetailView> {

    fun onTapGetImageButton()

    fun onTapOpenCameraButton()

    fun onTapGifButton()

    fun getUserId(): String
    fun sendMessage(senderId: String, receiverId: String, timeStamp: Long, message: PrivateMessageVO)

    fun getMessages(
        senderId: String,
        receiverId: String
    )

    fun uploadAndSendImage(bitmap: Bitmap)

    fun uploadGif(gifUrl:String,context:Context)

    fun sendGroupMessage(groupId: Long, timeStamp:Long, message:PrivateMessageVO)

    fun getGroupMessages(
        groupId:Long
    )
}