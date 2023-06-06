package com.flexath.moments.mvp.interfaces

import android.graphics.Bitmap
import com.flexath.moments.data.models.AuthenticationModel
import com.flexath.moments.data.models.UserModel
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.mvp.views.ProfileView

interface ProfilePresenter : BasePresenter<ProfileView> {
    var mAuthModel:AuthenticationModel
    var mUserModel:UserModel

    fun onTapEditProfileButton()

    fun onTapQrCodeImage()

    fun onTapGalleryImage()

    fun getUserId(): String

    fun updateUserInformation(user:UserVO)

    fun updateAndUploadProfileImage(bitmap: Bitmap, user: UserVO)
}