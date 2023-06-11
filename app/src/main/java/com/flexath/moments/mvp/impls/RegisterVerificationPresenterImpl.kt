package com.flexath.moments.mvp.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.moments.data.models.ChatModel
import com.flexath.moments.data.models.ChatModelImpl
import com.flexath.moments.mvp.interfaces.RegisterVerificationPresenter
import com.flexath.moments.mvp.views.RegisterVerificationView

class RegisterVerificationPresenterImpl : RegisterVerificationPresenter , ViewModel() {

    private var mView:RegisterVerificationView? = null
    private var mChatModel:ChatModel = ChatModelImpl

    override fun initPresenter(view: RegisterVerificationView) {
        mView = view
    }

    override fun onUIReady(lifecycleOwner: LifecycleOwner) {
        mChatModel.getOtp(
            onSuccess = {
                mView?.showOtp(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapBackButton() {
        mView?.navigateToPreviousScreen()
    }

    override fun onTapVerifyButton() {
        mView?.navigateToRegisterScreen()
    }
}