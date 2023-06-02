package com.flexath.moments.mvp.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.moments.mvp.interfaces.LoginPresenter
import com.flexath.moments.mvp.views.LoginView

class LoginPresenterImpl : LoginPresenter , ViewModel() {

    private var mView:LoginView? = null

    override fun initPresenter(view: LoginView) {
        mView = view
    }

    override fun onUIReady(context: Context, lifecycleOwner: LifecycleOwner) {

    }

    override fun onTapBackButton() {
        mView?.navigateToPreviousScreen()
    }

    override fun onTapLoginButton() {
        mView?.navigateToHomeScreen()
    }
}