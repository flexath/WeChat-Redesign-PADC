package com.flexath.moments.data.vos

data class GroupVO(
    var name:String = "",
    var userIdList:List<String> = listOf(),
    var messageList:List<GroupMessageVO> = listOf()
)
