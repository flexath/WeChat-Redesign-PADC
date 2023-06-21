package com.flexath.moments.data.vos

data class UserVO(
    val userId:String = "",
    val userName:String = "",
    val phoneNumber:String = "",
    val email: String = "",
    val password: String = "",
    val birthDate:String = "",
    val gender:String = "",
    val qrCode:String = "",
    var imageUrl:String = "",
    var fcmKey:String = ""
)
