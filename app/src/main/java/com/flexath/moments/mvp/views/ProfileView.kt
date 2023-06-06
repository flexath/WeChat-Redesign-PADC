package com.flexath.moments.mvp.views

import com.flexath.moments.data.vos.UserVO

interface ProfileView  : BaseView {

    fun showUserInformation(userList:List<UserVO>)

    fun showEditProfileDialog()
    fun showQrCodeDialog()
    fun showGallery()
}