package com.flexath.moments.mvp.interfaces

import android.graphics.Bitmap
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.mvp.views.RegisterView

interface RegisterPresenter : BasePresenter<RegisterView> {

    fun onTapBackButton()

    fun onTapOpenCameraButton()
    fun onTapSignUpButton(user: UserVO,bitmap: Bitmap)

    fun onTapProfileImage()

    fun onPhotoTaken(bitmap: Bitmap)
}