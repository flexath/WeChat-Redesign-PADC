package com.flexath.moments.data.models

import android.graphics.Bitmap
import android.util.Log
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.network.storage.CloudFireStoreFirebaseApiImpl
import com.flexath.moments.network.storage.FirebaseApi

object UserModelImpl : UserModel {

    override var mFirebaseApi: FirebaseApi = CloudFireStoreFirebaseApiImpl

    override fun addUser(user: UserVO) {
        mFirebaseApi.addUser(user)
    }

    override fun uploadProfileImage(bitmap: Bitmap, user: UserVO) {
        mFirebaseApi.uploadProfileImage(bitmap,user)
    }
}