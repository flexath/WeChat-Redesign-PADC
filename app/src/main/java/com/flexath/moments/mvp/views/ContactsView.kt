package com.flexath.moments.mvp.views

import com.flexath.moments.data.vos.UserVO

interface ContactsView  : BaseView {
    fun navigateToNewGroupScreen()
    fun navigateToNewContactScreen()
    fun showContacts(contactList: List<UserVO>)
    fun navigateToChatDetailScreen(userId: String)
    fun addUserToGroup(userId: String)
}