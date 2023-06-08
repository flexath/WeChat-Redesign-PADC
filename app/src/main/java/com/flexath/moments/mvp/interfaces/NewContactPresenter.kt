package com.flexath.moments.mvp.interfaces

import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.mvp.views.NewContactView

interface NewContactPresenter : BasePresenter<NewContactView> {
    fun getUsers(qrExporterUserId:String)
    fun createContact(scannerId:String,qrExporterId:String,contact: UserVO)
    fun getScannerUserId(): String
}