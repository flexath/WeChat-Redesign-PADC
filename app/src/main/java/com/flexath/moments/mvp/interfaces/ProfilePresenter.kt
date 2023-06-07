package com.flexath.moments.mvp.interfaces

import android.graphics.Bitmap
import com.flexath.moments.data.models.AuthenticationModel
import com.flexath.moments.data.models.MomentModel
import com.flexath.moments.data.models.UserModel
import com.flexath.moments.data.vos.MomentVO
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.delegates.MomentItemActionDelegate
import com.flexath.moments.mvp.views.ProfileView

interface ProfilePresenter : BasePresenter<ProfileView> , MomentItemActionDelegate {
    var mAuthModel:AuthenticationModel
    var mUserModel:UserModel
    var mMomentModel: MomentModel

    fun onTapEditProfileButton()

    fun onTapQrCodeImage()

    fun onTapGalleryImage()

    fun getUserId(): String

    fun updateUserInformation(user:UserVO)

    fun updateAndUploadProfileImage(bitmap: Bitmap, user: UserVO)

    fun createMoment(moment: MomentVO)
}