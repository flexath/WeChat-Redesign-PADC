package com.flexath.moments.data.models

import android.graphics.Bitmap
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.network.storage.CloudFireStoreFirebaseApiImpl
import com.flexath.moments.network.storage.CloudFireStoreFirebaseApi

object UserModelImpl : UserModel {

    override var mFirebaseApi: CloudFireStoreFirebaseApi = CloudFireStoreFirebaseApiImpl

    override fun addUser(user: UserVO) {
        mFirebaseApi.addUser(user)
    }

    override fun updateAndUploadProfileImage(bitmap: Bitmap, user: UserVO) {
        mFirebaseApi.updateAndUploadProfileImage(bitmap,user)
    }

    override fun getUsers(onSuccess: (users: List<UserVO>) -> Unit, onFailure: (String) -> Unit) {
        mFirebaseApi.getUsers(onSuccess = onSuccess , onFailure = onFailure)
    }

    override fun createContact(scannerId:String,qrExporterId:String,contact: UserVO) {
        mFirebaseApi.createContact(scannerId,qrExporterId,contact)
    }

    override fun getContacts(
        scannerId: String,
        onSuccess: (users: List<UserVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getContacts(scannerId, onSuccess, onFailure)
    }
}