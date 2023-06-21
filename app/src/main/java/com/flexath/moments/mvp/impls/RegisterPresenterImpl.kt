package com.flexath.moments.mvp.impls

import android.graphics.Bitmap
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.moments.data.models.AuthenticationModel
import com.flexath.moments.data.models.AuthenticationModelImpl
import com.flexath.moments.data.models.UserModel
import com.flexath.moments.data.models.UserModelImpl
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.mvp.interfaces.RegisterPresenter
import com.flexath.moments.mvp.views.RegisterView

class RegisterPresenterImpl : RegisterPresenter , ViewModel() {

    private var mView:RegisterView? = null
    private val mUserModel:UserModel = UserModelImpl
    private val mAuthModel:AuthenticationModel = AuthenticationModelImpl

    override fun initPresenter(view: RegisterView) {
        mView = view
    }

    override fun onUIReady(lifecycleOwner: LifecycleOwner) {

    }

    override fun onTapBackButton() {
        mView?.navigateToPreviousScreen()
    }

    override fun onTapOpenCameraButton() {
        mView?.openCamera()
    }

    override fun onTapSignUpButton(user:UserVO,bitmap: Bitmap) {
        mAuthModel.register(
            userName = user.userName,
            phoneNumber = user.phoneNumber,
            email = user.email,
            password = user.password,
            birthDate = user.birthDate,
            gender = user.gender,
            imageUrl = user.imageUrl,
            fcmKey= user.fcmKey,
            onSuccess = {
                mUserModel.addUser(it)
                mUserModel.updateAndUploadProfileImage(bitmap, it)
                mView?.navigateToLoginScreen()
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapProfileImage() {
        mView?.showGallery()
    }

    override fun onPhotoTaken(bitmap: Bitmap) {

    }
}