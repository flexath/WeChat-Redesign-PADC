package com.flexath.moments.mvp.views

import com.flexath.moments.data.vos.UserVO

interface NewContactView : BaseView {
    fun getUsers(userList: List<UserVO>,qrExporterUserId:String)
}