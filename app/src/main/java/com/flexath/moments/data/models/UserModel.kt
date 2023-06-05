package com.flexath.moments.data.models

import android.graphics.Bitmap
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.network.storage.FirebaseApi

interface UserModel {
    var mFirebaseApi:FirebaseApi

    fun addUser(user: UserVO)

    fun uploadImageAndEditGrocery(bitmap: Bitmap, user: UserVO)
}