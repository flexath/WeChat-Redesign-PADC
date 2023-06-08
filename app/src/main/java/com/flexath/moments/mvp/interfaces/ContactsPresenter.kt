package com.flexath.moments.mvp.interfaces

import com.flexath.moments.delegates.AlphabetActionItemDelegate
import com.flexath.moments.delegates.ChatItemActionDelegate
import com.flexath.moments.delegates.GroupItemActionDelegate
import com.flexath.moments.mvp.views.ContactsView

interface ContactsPresenter  : BasePresenter<ContactsView>,GroupItemActionDelegate,AlphabetActionItemDelegate,ChatItemActionDelegate {
    fun onTapAddNewContactButton()

    fun getContacts(scannerId:String)

    fun getUserId() :String
}