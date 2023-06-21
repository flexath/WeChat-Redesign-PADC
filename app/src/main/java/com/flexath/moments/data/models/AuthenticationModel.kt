package com.flexath.moments.data.models

import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.network.auth.AuthManager

interface AuthenticationModel {
    var mAuthManager:AuthManager

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