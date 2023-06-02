package com.flexath.moments.mvp.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.moments.mvp.interfaces.RegisterVerificationPresenter
import com.flexath.moments.mvp.views.RegisterVerificationView

class RegisterVerificationPresenterImpl : RegisterVerificationPresenter , ViewModel() {

    private var mView:RegisterVerificationView? = null

    override fun initPresenter(view: RegisterVerificationView) {
        mView = view
    }

    override fun onUIReady(context: Context, lifecycleOwner: LifecycleOwner) {

    }

    override fun onTapBackButton() {
        mView?.navigateToPreviousScreen()
    }
}