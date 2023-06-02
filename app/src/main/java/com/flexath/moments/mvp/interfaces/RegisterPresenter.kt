package com.flexath.moments.mvp.interfaces

import com.flexath.moments.mvp.views.RegisterView

interface RegisterPresenter : BasePresenter<RegisterView> {

    fun onTapBackButton()
    fun onTapSignUpButton()
}