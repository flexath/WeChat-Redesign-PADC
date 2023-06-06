package com.flexath.moments.mvp.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.moments.data.models.AuthenticationModel
import com.flexath.moments.data.models.AuthenticationModelImpl
import com.flexath.moments.data.models.MomentModel
import com.flexath.moments.data.models.MomentModelImpl
import com.flexath.moments.data.models.UserModel
import com.flexath.moments.data.models.UserModelImpl
import com.flexath.moments.mvp.interfaces.NewMomentPresenter
import com.flexath.moments.mvp.views.NewMomentView

class NewMomentPresenterImpl: NewMomentPresenter , ViewModel() {

    private var mView:NewMomentView? = null

    override var mAuthModel: AuthenticationModel = AuthenticationModelImpl
    override var mMomentModel: MomentModel = MomentModelImpl
    override var mUserModel: UserModel = UserModelImpl

    override fun initPresenter(view: NewMomentView) {
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

    override fun onTapAddImageButton() {
        mView?.showGallery()
    }

    override fun onTapBackButton() {
        mView?.navigateToPreviousScreen()
    }

    override fun onTapCreateButton() {

        mView?.createNewMoment()
    }

    override fun getUserId(): String {
        return mAuthModel.getUserId()
    }

}