package com.flexath.moments.data.models

import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.network.auth.AuthManager
import com.flexath.moments.network.auth.FirebaseAuthManager

object AuthenticationModelImpl : AuthenticationModel {

    override var mAuthManager: AuthManager = FirebaseAuthManager

    override fun login(
        phoneNumber: String,
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        mAuthManager.login(phoneNumber,email, password, onSuccess, onFailure)
    }

    override fun register(
        userName: String,
        phoneNumber: String,
        email: String,
        password: String,
        birthDate: String,
        gender: String,
        onSuccess: (user: UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mAuthManager.register(userName, phoneNumber, email, password, birthDate, gender, onSuccess, onFailure)
    }

    override fun getUserId(): String {
        return mAuthManager.getUserId()
    }
}