package com.flexath.moments.mvp.views

interface RegisterVerificationView : BaseView {
    fun navigateToPreviousScreen()
    fun navigateToRegisterScreen()
    fun showOtp(otp: String)
}