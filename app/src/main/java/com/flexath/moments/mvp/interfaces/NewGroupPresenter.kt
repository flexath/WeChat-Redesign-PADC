package com.flexath.moments.mvp.interfaces

import android.graphics.Bitmap
import com.flexath.moments.delegates.AlphabetActionItemDelegate
import com.flexath.moments.delegates.ChatItemActionDelegate
import com.flexath.moments.mvp.views.NewGroupView

interface NewGroupPresenter : BasePresenter<NewGroupView> , AlphabetActionItemDelegate ,ChatItemActionDelegate {

    fun getContacts(scannerId:String)

    fun getUserId() :String

    fun onTapCreateButton(timeStamp: Long, groupName: String,userList:List<String>,imageUrl:String)
    fun onTapProfileImage()
    fun onTapOpenCameraButton()

    fun uploadGroupCoverPhoto(timeStamp: Long, bitmap: Bitmap)
}