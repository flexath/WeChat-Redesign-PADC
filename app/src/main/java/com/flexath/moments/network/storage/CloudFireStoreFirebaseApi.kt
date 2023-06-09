package com.flexath.moments.network.storage

import android.graphics.Bitmap
import com.flexath.moments.data.vos.MomentVO
import com.flexath.moments.data.vos.UserVO

interface CloudFireStoreFirebaseApi {
    fun addUser(user: UserVO)
    fun updateAndUploadProfileImage(bitmap: Bitmap, user: UserVO)
    fun getUsers(
        onSuccess: (users: List<UserVO>) -> Unit, onFailure: (String) -> Unit
    )

    fun createMoment(moment: MomentVO)
    fun updateAndUploadMomentImage(bitmap: Bitmap)

    fun getMomentImages()  : String
    fun getMoments(
        onSuccess: (moments: List<MomentVO>) -> Unit, onFailure: (String) -> Unit
    )

    fun createContact(scannerId:String,qrExporterId:String,contact: UserVO)

    fun getContacts(scannerId:String,onSuccess: (users: List<UserVO>) -> Unit, onFailure: (String) -> Unit)
}