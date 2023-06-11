package com.flexath.moments.delegates

interface ChatItemActionDelegate {
    fun onTapChatItem(userId:String)
    fun onTapCheckbox(userId: String,isCheck:Boolean)
}