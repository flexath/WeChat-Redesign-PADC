package com.flexath.moments.mvp.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.moments.data.models.AuthenticationModel
import com.flexath.moments.data.models.AuthenticationModelImpl
import com.flexath.moments.mvp.interfaces.LoginPresenter
import com.flexath.moments.mvp.views.LoginView

class LoginPresenterImpl : LoginPresenter, ViewModel() {

    private var mView: LoginView? = null
    private val mAuthModel: AuthenticationModel = AuthenticationModelImpl

    override fun initPresenter(view: LoginView) {
        mView = view
    }

    override fun onUIReady(lifecycleOwner: LifecycleOwner) {

    }

    override fun onTapBackButton() {
        mView?.navigateToPreviousScreen()
    }

    override fun onTapLoginButton(phoneNumber: String, email: String, password: String) {
        mAuthModel.login(
            phoneNumber,
            email,
            password,
            onSuccess = {
                mView?.navigateToHomeScreen()
            },
            onFailure = {
                mView?.showError(it)
            })
    }
}