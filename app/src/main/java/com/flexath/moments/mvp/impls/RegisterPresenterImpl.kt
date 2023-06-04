package com.flexath.moments.mvp.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.moments.mvp.interfaces.RegisterPresenter
import com.flexath.moments.mvp.views.RegisterView

class RegisterPresenterImpl : RegisterPresenter , ViewModel() {

    private var mView:RegisterView? = null

    override fun initPresenter(view: RegisterView) {
        mView = view
    }

    override fun onUIReady(context: Context, lifecycleOwner: LifecycleOwner) {

    }

    override fun onTapBackButton() {
        mView?.navigateToPreviousScreen()
    }

    override fun onTapSignUpButton() {
        mView?.navigateToLoginScreen()
    }
}