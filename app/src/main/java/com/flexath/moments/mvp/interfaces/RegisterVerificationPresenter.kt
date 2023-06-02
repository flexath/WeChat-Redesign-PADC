package com.flexath.moments.mvp.interfaces

import com.flexath.moments.mvp.views.RegisterVerificationView

interface RegisterVerificationPresenter : BasePresenter<RegisterVerificationView> {
    fun onTapBackButton()
}