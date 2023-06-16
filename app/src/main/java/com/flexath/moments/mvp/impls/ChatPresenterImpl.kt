package com.flexath.moments.mvp.impls

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.moments.data.models.AuthenticationModel
import com.flexath.moments.data.models.AuthenticationModelImpl
import com.flexath.moments.data.models.ChatModel
import com.flexath.moments.data.models.ChatModelImpl
import com.flexath.moments.data.models.UserModel
import com.flexath.moments.data.models.UserModelImpl
import com.flexath.moments.mvp.interfaces.ChatPresenter
import com.flexath.moments.mvp.views.ChatView

class ChatPresenterImpl : ChatPresenter , ViewModel() {

    private var mView:ChatView? = null
    private var mUserModel: UserModel = UserModelImpl
    private var mAuthModel:AuthenticationModel = AuthenticationModelImpl
    private var mChatModel:ChatModel = ChatModelImpl

    override fun initPresenter(view: ChatView) {
        mView = view
    }

    override fun onUIReady(lifecycleOwner: LifecycleOwner) {
        mUserModel.getUsers(
            onSuccess = {
                mView?.getUsers(it)
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

    override fun onTapChatItem(userId: String) {
        mView?.navigateToChatDetailScreen(userId)
    }

    override fun onTapCheckbox(userId: String, isCheck: Boolean) {}
    override fun onTapGroupItem(groupId: Long) {
        mView?.navigateToGroupChatDetailScreen(groupId)
    }

    override fun getContacts(
        scannerId: String
    ) {
        mUserModel.getContacts(
            scannerId,
            onSuccess = {
                mView?.showContacts(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun getUserId(): String {
        return mAuthModel.getUserId()
    }

    override fun getChatHistoryUserId(senderId: String) {
        mChatModel.getChatHistoryUserId(
            senderId,
            onSuccess = {
                mView?.showUserId(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun getLastMessage(senderId: String, receiverId: String) {
        mChatModel.getLastMessage(
            senderId = senderId,
            receiverId = receiverId,
            onSuccess = {
                mView?.getLastMessage(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun getGroupMessages(groupId: Long, onSuccess: (Int) -> Unit) {
        mChatModel.getGroupMessages(
            groupId,
            onSuccess = {
                onSuccess(it.size)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

}