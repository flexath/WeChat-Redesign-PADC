package com.flexath.moments.mvp.interfaces

import com.flexath.moments.delegates.GroupItemActionDelegate
import com.flexath.moments.mvp.views.ContactsView

interface ContactsPresenter  : BasePresenter<ContactsView>,GroupItemActionDelegate {
    fun onTapAddNewContactButton()

    fun getContacts(scannerId:String)

    fun getUserId() :String
}