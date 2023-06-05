package com.flexath.moments.mvp.interfaces

import android.graphics.Bitmap
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.mvp.views.RegisterView

interface RegisterPresenter : BasePresenter<RegisterView> {

    fun onTapBackButton()
    fun onTapSignUpButton(user: UserVO)

    fun onTapProfileImage(user:UserVO)

    fun onPhotoTaken(bitmap: Bitmap)
}