package com.flexath.moments.mvp.impls

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.moments.data.models.AuthenticationModel
import com.flexath.moments.data.models.AuthenticationModelImpl
import com.flexath.moments.data.models.ChatModel
import com.flexath.moments.data.models.ChatModelImpl
import com.flexath.moments.data.models.UserModel
import com.flexath.moments.data.models.UserModelImpl
import com.flexath.moments.data.vos.MessageVO
import com.flexath.moments.mvp.interfaces.ChatDetailPresenter
import com.flexath.moments.mvp.views.ChatDetailView

class ChatDetailPresenterImpl : ChatDetailPresenter, ViewModel() {

    private var mView: ChatDetailView? = null
    private val mChatModel: ChatModel = ChatModelImpl
    private val mAuthModel: AuthenticationModel = AuthenticationModelImpl
    private val mUserModel: UserModel = UserModelImpl

    override fun initPresenter(view: ChatDetailView) {
        mView = view
    }

    override fun onUIReady(lifecycleOwner: LifecycleOwner) {
        mUserModel.getUsers(
            onSuccess = {
                mView?.showUsers(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )

        mChatModel.getGroups(
            onSuccess = {
                mView?.getGroups(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapGetImageButton() {
        mView?.showGallery()
    }

    override fun onTapOpenCameraButton() {
        mView?.openCamera()
    }

    override fun getUserId(): String {
        return mAuthModel.getUserId()
    }

    override fun sendMessage(
        senderId: String,
        receiverId: String,
        timeStamp: Long,
        message: MessageVO
    ) {
        mChatModel.sendMessage(senderId, receiverId, timeStamp, message)
    }

    override fun getMessages(senderId: String, receiverId: String) {
        mChatModel.getMessages(
            senderId,
            receiverId,
            onSuccess = { messageList ->
                val sortedMessages = messageList.sortedBy { it.timeStamp }
                mView?.showMessages(sortedMessages)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun uploadAndSendImage(
        bitmap: Bitmap
    ) {
        mChatModel.uploadAndSendImage(
            bitmap,
            onSuccess = {
                mView?.getImageUrlForFile(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun sendGroupMessage(groupId: Long, timeStamp: Long, message: MessageVO) {
        mChatModel.sendGroupMessage(groupId, timeStamp, message)
    }

    override fun getGroupMessages(groupId: Long) {
        mChatModel.getGroupMessages(
            groupId,
            onSuccess = { messageList ->
                val sortedMessages = messageList.sortedBy { it.timeStamp }
                mView?.showGroupMessages(sortedMessages)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }
}