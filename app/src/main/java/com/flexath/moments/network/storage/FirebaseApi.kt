package com.flexath.moments.network.storage

import android.graphics.Bitmap
import com.flexath.moments.data.vos.UserVO

interface FirebaseApi {
    fun addUser(user:UserVO)
    fun uploadImageAndEditGrocery(bitmap: Bitmap, user: UserVO)
}