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
import com.flexath.moments.mvp.interfaces.RegisterPresenter
import com.flexath.moments.mvp.views.RegisterView

class RegisterPresenterImpl : RegisterPresenter , ViewModel() {

    private var mView:RegisterView? = null
    private val mUserModel:UserModel = UserModelImpl
    private val mAuthModel:AuthenticationModel = AuthenticationModelImpl

    private var mUserForProfileImageFromGallery:UserVO? = null

    override fun initPresenter(view: RegisterView) {
        mView = view
    }

    override fun onUIReady(context: Context, lifecycleOwner: LifecycleOwner) {

    }

    override fun onTapBackButton() {
        mView?.navigateToPreviousScreen()
    }

    override fun onTapSignUpButton(user:UserVO) {
        mAuthModel.register(
            userName = user.userName,
            phoneNumber = user.phoneNumber,
            email = user.email,
            password = user.password,
            birthDate = user.birthDate,
            gender = user.gender,
            onSuccess = {
                mUserModel.addUser(it)
                mView?.navigateToLoginScreen()
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapProfileImage(user:UserVO) {
        mUserForProfileImageFromGallery = user
        mView?.showGallery()
    }

    override fun onPhotoTaken(bitmap: Bitmap) {
        mUserForProfileImageFromGallery?.let { user ->
            mUserModel.uploadImageAndEditGrocery(bitmap, user)
        }
    }
}