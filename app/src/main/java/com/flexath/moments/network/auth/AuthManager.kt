package com.flexath.moments.network.auth

import com.flexath.moments.data.vos.UserVO

interface AuthManager {

    fun login(phoneNumber: String,email: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit)

    fun register(
        userName:String,
        phoneNumber:String,
        email: String,
        password: String,
        birthDate:String,
        gender:String,
        imageUrl:String,
        fcmKey:String,
        onSuccess: (user: UserVO) -> Unit,
        onFailure: (String) -> Unit
    )

    fun getUserId() : String
}