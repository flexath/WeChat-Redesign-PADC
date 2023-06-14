package com.flexath.moments.mvp.interfaces

import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.delegates.ChatItemActionDelegate
import com.flexath.moments.delegates.GroupItemActionDelegate
import com.flexath.moments.mvp.views.ChatView

interface ChatPresenter  : BasePresenter<ChatView> , ChatItemActionDelegate , GroupItemActionDelegate {
    fun getContacts(scannerId:String)

    fun getUserId() : String

    fun getChatHistoryUserId(
        senderId: String
    )
    fun getGroupMessages(groupId: Long, onSuccess: (Int) -> Unit)
}