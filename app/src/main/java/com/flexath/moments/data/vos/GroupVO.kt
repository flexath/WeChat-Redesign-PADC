package com.flexath.moments.data.vos

data class GroupVO(
    var id:Long = 0L,
    var name:String = "",
    var userIdList:List<String> = listOf(),
    var messageList:List<MessageVO> = listOf()
)
