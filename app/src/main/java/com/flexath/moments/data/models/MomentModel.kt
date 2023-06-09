package com.flexath.moments.data.models

import android.graphics.Bitmap
import com.flexath.moments.data.vos.MomentVO
import com.flexath.moments.network.storage.CloudFireStoreFirebaseApi

interface MomentModel {
    var mFirebaseApi: CloudFireStoreFirebaseApi

    fun createMoment(moment: MomentVO)

    fun updateAndUploadMomentImage(bitmap: Bitmap)

    fun getMomentImages(): String

    fun getMoments(
        onSuccess: (moments: List<MomentVO>) -> Unit, onFailure: (String) -> Unit
    )
}