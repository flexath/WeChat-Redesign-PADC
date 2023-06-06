package com.flexath.moments.mvp.impls

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.moments.data.models.AuthenticationModel
import com.flexath.moments.data.models.AuthenticationModelImpl
import com.flexath.moments.data.models.UserModel
import com.flexath.moments.data.models.UserModelImpl
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.mvp.interfaces.ProfilePresenter
import com.flexath.moments.mvp.views.ProfileView

class ProfilePresenterImpl : ProfilePresenter , ViewModel() {

    override var mAuthModel: AuthenticationModel = AuthenticationModelImpl
    override var mUserModel: UserModel = UserModelImpl

    private var mView:ProfileView? = null
    override fun initPresenter(view: ProfileView) {
        mView = view
    }

    override fun onUIReady(context: Context, lifecycleOwner: LifecycleOwner) {
        mUserModel.getUsers(
            onSuccess = {
                mView?.showUserInformation(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun getUserId(): String {
        return mAuthModel.getUserId()
    }

    override fun updateUserInformation(user: UserVO) {
        mUserModel.addUser(user)
    }

    override fun updateAndUploadProfileImage(bitmap: Bitmap, user: UserVO) {
        mUserModel.updateAndUploadProfileImage(bitmap,user)
    }

    override fun onTapEditProfileButton() {
        mView?.showEditProfileDialog()
    }

    override fun onTapQrCodeImage() {
        mView?.showQrCodeDialog()
    }

    override fun onTapGalleryImage() {
        mView?.showGallery()
    }
}