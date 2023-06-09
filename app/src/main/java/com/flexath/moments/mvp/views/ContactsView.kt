package com.flexath.moments.mvp.views

import com.flexath.moments.data.vos.GroupVO
import com.flexath.moments.data.vos.UserVO

interface ContactsView  : BaseView {
    fun navigateToNewContactScreen()
    fun showContacts(contactList: List<UserVO>)
    fun navigateToChatDetailScreen(userId: String)
    fun addUserToGroup(userId: String)
    fun getGroupList(groupList: List<GroupVO>)
    fun navigateToChatDetailScreenFromGroupItem(groupId: String)
}