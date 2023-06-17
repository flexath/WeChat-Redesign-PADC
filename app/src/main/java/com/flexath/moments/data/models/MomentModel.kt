package com.flexath.moments.data.models

import android.graphics.Bitmap
import com.flexath.moments.data.vos.MomentVO
import com.flexath.moments.network.storage.CloudFireStoreFirebaseApi

interface MomentModel {
    var mFirebaseApi: CloudFireStoreFirebaseApi

    fun createMoment(moment: MomentVO)

    fun deleteMoment(
        momentId: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun updateAndUploadMomentImage(bitmap: Bitmap)

    fun getMomentImages(): String

    fun clearMomentImages()

    fun getMoments(
        onSuccess: (moments: List<MomentVO>) -> Unit, onFailure: (String) -> Unit
    )

    fun addMomentToUserBookmarked(currentUserId:String,moment: MomentVO)
    fun deleteMomentFromUserBookmarked(currentUserId: String,momentId:String)

    fun getMomentsFromUserBookmarked(
        currentUserId: String,
        onSuccess: (moments: List<MomentVO>) -> Unit,
        onFailure: (String) -> Unit
    )
}