package com.flexath.moments.mvp.interfaces

import com.flexath.moments.delegates.AlphabetActionItemDelegate
import com.flexath.moments.delegates.ChatItemActionDelegate
import com.flexath.moments.mvp.views.NewGroupView

interface NewGroupPresenter : BasePresenter<NewGroupView> , AlphabetActionItemDelegate ,ChatItemActionDelegate {

    fun getContacts(scannerId:String)

    fun getUserId() :String
}