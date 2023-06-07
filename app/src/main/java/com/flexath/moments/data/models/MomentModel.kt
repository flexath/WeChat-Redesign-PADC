package com.flexath.moments.data.models

import android.graphics.Bitmap
import com.flexath.moments.data.vos.MomentVO
import com.flexath.moments.network.storage.FirebaseApi

interface MomentModel {
    var mFirebaseApi: FirebaseApi

    fun createMoment(moment: MomentVO)

    fun updateAndUploadMomentImage(bitmap: Bitmap)

    fun getMomentImages(): String

    fun getMoments(
        onSuccess: (moments: List<MomentVO>) -> Unit, onFailure: (String) -> Unit
    )
}