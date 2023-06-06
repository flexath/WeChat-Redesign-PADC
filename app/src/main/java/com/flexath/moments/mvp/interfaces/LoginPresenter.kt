package com.flexath.moments.mvp.interfaces

import com.flexath.moments.mvp.views.LoginView

interface LoginPresenter : BasePresenter<LoginView> {
    fun onTapBackButton()
    fun onTapLoginButton(phoneNumber: String, email: String, password: String)

}