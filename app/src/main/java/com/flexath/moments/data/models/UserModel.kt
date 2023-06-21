package com.flexath.moments.data.models

import android.graphics.Bitmap
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.data.vos.fcm.FCMBody
import com.flexath.moments.network.retrofit.responses.FCMResponse
import com.flexath.moments.network.storage.CloudFireStoreFirebaseApi

interface UserModel {

    var mFirebaseApi:CloudFireStoreFirebaseApi

    fun addUser(user: UserVO)

    fun updateAndUploadProfileImage(bitmap: Bitmap, user: UserVO)

    fun getUsers(onSuccess: (users: List<UserVO>) -> Unit, onFailure: (String) -> Unit)

    fun getSpecificUser(userId: String,onSuccess: (users: UserVO) -> Unit, onFailure: (String) -> Unit)

    fun createContact(scannerId:String,qrExporterId:String,contact: UserVO)

    fun getContacts(scannerId:String,onSuccess: (users: List<UserVO>) -> Unit, onFailure: (String) -> Unit)

    fun sendFCMNotification(
        fcmBody: FCMBody,
        onSuccess: (FCMResponse) -> Unit,
        onFailure: (String) -> Unit
    )

}