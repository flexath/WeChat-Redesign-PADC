package com.flexath.moments.data.vos

data class MomentVO(
    var id:String = "",
    var userId:String = "",
    var userName:String = "",
    var userProfileImage:String = "",
    var caption:String = "",
    var imageUrl:String = "",
    var isBookmarked:Boolean = false
)
