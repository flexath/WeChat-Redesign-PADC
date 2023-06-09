package com.flexath.moments.mvp.impls

import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.moments.data.models.AuthenticationModel
import com.flexath.moments.data.models.AuthenticationModelImpl
import com.flexath.moments.data.models.ChatModel
import com.flexath.moments.data.models.ChatModelImpl
import com.flexath.moments.data.models.UserModel
import com.flexath.moments.data.models.UserModelImpl
import com.flexath.moments.mvp.interfaces.NewGroupPresenter
import com.flexath.moments.mvp.views.NewGroupView

class NewGroupPresenterImpl : NewGroupPresenter, ViewModel() {

    private var mView: NewGroupView? = null
    private var mUserModel: UserModel = UserModelImpl
    private var mAuthModel: AuthenticationModel = AuthenticationModelImpl
    private var mChatModel: ChatModel = ChatModelImpl

    override fun initPresenter(view: NewGroupView) {
        mView = view
    }

    override fun onUIReady(lifecycleOwner: LifecycleOwner) {

    }

    override fun getContacts(scannerId: String) {
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

    override fun onTapCreateButton(
        timeStamp: Long,
        groupName: String,
        userList: List<String>,
        imageUrl: String
    ) {
        mChatModel.addGroup(timeStamp, groupName, userList, imageUrl)
    }

    override fun onTapAlphabetItem(position: Int) {}

    override fun onTapChatItem(userId: String) {
        mView?.navigateToChatDetailScreen(userId)
    }

    override fun onTapCheckbox(userId: String, isCheck: Boolean) {
        mView?.addUserToGroup(userId, isCheck)
    }

    override fun onTapProfileImage() {
        mView?.showGallery()
    }

    override fun onTapOpenCameraButton() {
        mView?.openCamera()
    }

    override fun uploadGroupCoverPhoto(timeStamp: Long, bitmap: Bitmap) {
        mChatModel.uploadGroupCoverPhoto(
            timeStamp,
            bitmap,
            onSuccess = {
                mView?.getGroupCoverImageUrl(it)
            },
            onFailure = {
                mView?.showError(it)
            })
    }
}