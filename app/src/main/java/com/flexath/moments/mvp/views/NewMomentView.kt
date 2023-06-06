package com.flexath.moments.mvp.views

import com.flexath.moments.data.vos.MomentVO
import com.flexath.moments.data.vos.UserVO

interface NewMomentView : BaseView {

    fun createNewMoment()

    fun navigateToPreviousScreen()
    fun showGallery()
    fun showUserInformation(userList: List<UserVO>)

}