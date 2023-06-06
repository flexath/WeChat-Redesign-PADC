package com.flexath.moments.data.models

import android.graphics.Bitmap
import com.flexath.moments.data.vos.MomentVO
import com.flexath.moments.network.storage.CloudFireStoreFirebaseApiImpl
import com.flexath.moments.network.storage.FirebaseApi

object MomentModelImpl : MomentModel {

    override var mFirebaseApi: FirebaseApi = CloudFireStoreFirebaseApiImpl

    override fun createMoment(moment: MomentVO) {
        mFirebaseApi.createMoment(moment)
    }

    override fun updateAndUploadMomentImage(bitmap: Bitmap, moment: MomentVO) {
        mFirebaseApi.updateAndUploadMomentImage(bitmap,moment)
    }

    override fun getMoments(
        onSuccess: (moments: List<MomentVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        mFirebaseApi.getMoments(onSuccess = onSuccess, onFailure = onFailure)
    }

}