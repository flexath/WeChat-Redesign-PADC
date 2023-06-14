package com.flexath.moments.mvp.views

import com.flexath.moments.data.vos.GroupVO
import com.flexath.moments.data.vos.PrivateMessageVO
import com.flexath.moments.data.vos.UserVO

interface ChatView  : BaseView {
    fun showContacts(contactList: List<UserVO>)
    fun navigateToChatDetailScreen(userId: String)
    fun showUserId(userIdList: List<String>)
    fun getUsers(userList: List<UserVO>)
    fun getGroups(groupList: List<GroupVO>)
    fun navigateToGroupChatDetailScreen(groupId: Long)
}