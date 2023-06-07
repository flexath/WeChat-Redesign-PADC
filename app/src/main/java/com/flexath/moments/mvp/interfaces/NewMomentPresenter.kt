package com.flexath.moments.mvp.interfaces

import android.graphics.Bitmap
import com.flexath.moments.data.models.AuthenticationModel
import com.flexath.moments.data.models.MomentModel
import com.flexath.moments.data.models.UserModel
import com.flexath.moments.data.vos.MomentVO
import com.flexath.moments.delegates.NewMomentImageDelegate
import com.flexath.moments.mvp.views.NewMomentView

interface NewMomentPresenter : BasePresenter<NewMomentView> , NewMomentImageDelegate {

    var mAuthModel: AuthenticationModel
    var mMomentModel: MomentModel
    var mUserModel: UserModel
    fun onTapBackButton()
    fun onTapCreateButton(moment: MomentVO)

    fun createMomentImages(bitmap: Bitmap)

    fun getMomentImages(): String

    fun getUserId(): String
}