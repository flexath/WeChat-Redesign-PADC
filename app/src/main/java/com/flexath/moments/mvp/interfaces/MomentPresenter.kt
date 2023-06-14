package com.flexath.moments.mvp.interfaces

import com.flexath.moments.data.vos.MomentVO
import com.flexath.moments.delegates.MomentItemActionDelegate
import com.flexath.moments.mvp.views.MomentView

interface MomentPresenter  : BasePresenter<MomentView> , MomentItemActionDelegate {
    fun onTapAddMomentButton()

    fun createMoment(moment:MomentVO)

    fun getUserId(): String

    fun addMomentToUserBookmarked(currentUserId:String,moment: MomentVO)

    fun deleteMomentFromUserBookmarked(currentUserId: String,momentId:String)

    fun getMomentsFromUserBookmarked(currentUserId: String)
}