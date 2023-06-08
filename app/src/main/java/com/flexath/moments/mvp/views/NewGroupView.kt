package com.flexath.moments.mvp.views

import com.flexath.moments.data.vos.UserVO

interface NewGroupView : BaseView {


    fun addUserToGroup(userId: String)

    fun navigateToChatDetailScreen(userId: String)
    fun showContacts(userList: List<UserVO>)
}