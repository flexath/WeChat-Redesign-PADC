package com.flexath.moments.network.storage

import android.graphics.Bitmap
import com.flexath.moments.data.vos.UserVO

interface FirebaseApi {
    fun addUser(user:UserVO)
    fun uploadProfileImage(bitmap: Bitmap, user: UserVO)
}