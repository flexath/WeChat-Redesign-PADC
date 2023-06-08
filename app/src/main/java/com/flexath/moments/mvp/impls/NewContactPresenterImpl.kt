package com.flexath.moments.mvp.impls

import android.content.Context
import androidx.core.graphics.rotationMatrix
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.moments.data.models.AuthenticationModel
import com.flexath.moments.data.models.AuthenticationModelImpl
import com.flexath.moments.data.models.UserModel
import com.flexath.moments.data.models.UserModelImpl
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.mvp.interfaces.NewContactPresenter
import com.flexath.moments.mvp.views.NewContactView

class NewContactPresenterImpl : NewContactPresenter , ViewModel() {

    private var mView:NewContactView? = null
    private val mUserModel:UserModel = UserModelImpl
    private val mAuthModel:AuthenticationModel = AuthenticationModelImpl

    override fun initPresenter(view: NewContactView) {
        mView = view
    }

    override fun onUIReady(context: Context, lifecycleOwner: LifecycleOwner) {

    }

    override fun getUsers(qrExporterUserId:String) {
        mUserModel.getUsers(
            onSuccess = {
                mView?.getUsers(it,qrExporterUserId)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun createContact(scannerId:String,qrExporterId:String,contact: UserVO) {
        mUserModel.createContact(scannerId, qrExporterId, contact)
    }

    override fun getScannerUserId(): String {
        return mAuthModel.getUserId()
    }
}