package com.flexath.moments.mvp.views

import com.flexath.moments.data.vos.GroupVO
import com.flexath.moments.data.vos.PrivateMessageVO
import com.flexath.moments.data.vos.UserVO

interface ChatDetailView : BaseView {
    fun showUsers(userList: List<UserVO>)
    fun showMessages(messageList: List<PrivateMessageVO>)
    fun showGallery()
    fun getImageUrlForFile(file: String)
    fun showGroupMessages(messageList: List<PrivateMessageVO>)
    fun getGroups(groupList: List<GroupVO>)
    fun openCamera()

}