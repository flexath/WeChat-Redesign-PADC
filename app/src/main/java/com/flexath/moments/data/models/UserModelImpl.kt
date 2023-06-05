package com.flexath.moments.data.models

import android.graphics.Bitmap
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.network.storage.CloudFireStoreFirebaseApiImpl
import com.flexath.moments.network.storage.FirebaseApi

object UserModelImpl : UserModel {

    override var mFirebaseApi: FirebaseApi = CloudFireStoreFirebaseApiImpl

    override fun addUser(user: UserVO) {
        mFirebaseApi.addUser(user)
    }

    override fun uploadImageAndEditGrocery(bitmap: Bitmap, user: UserVO) {
        mFirebaseApi.uploadImageAndEditGrocery(bitmap,user)
    }
}