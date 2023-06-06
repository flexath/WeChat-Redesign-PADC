package com.flexath.moments.mvp.interfaces

import com.flexath.moments.data.models.AuthenticationModel
import com.flexath.moments.data.models.MomentModel
import com.flexath.moments.data.models.UserModel
import com.flexath.moments.delegates.NewMomentImageDelegate
import com.flexath.moments.mvp.views.NewMomentView

interface NewMomentPresenter : BasePresenter<NewMomentView> , NewMomentImageDelegate {

    var mAuthModel: AuthenticationModel
    var mMomentModel: MomentModel
    var mUserModel: UserModel
    fun onTapBackButton()
    fun onTapCreateButton()

    fun getUserId(): String
}