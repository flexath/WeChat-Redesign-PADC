package com.flexath.moments.mvp.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.moments.data.models.AuthenticationModel
import com.flexath.moments.data.models.AuthenticationModelImpl
import com.flexath.moments.data.models.UserModel
import com.flexath.moments.data.models.UserModelImpl
import com.flexath.moments.mvp.interfaces.ContactsPresenter
import com.flexath.moments.mvp.views.ContactsView

class ContactsPresenterImpl : ContactsPresenter , ViewModel() {

    private var mView:ContactsView? = null
    private var mUserModel:UserModel = UserModelImpl
    private var mAuthModel:AuthenticationModel = AuthenticationModelImpl

    override fun initPresenter(view: ContactsView) {
        mView = view
    }

    override fun onUIReady(context: Context, lifecycleOwner: LifecycleOwner) {

    }

    override fun onTapAddNewContactButton() {
        mView?.navigateToNewContactScreen()
    }

    override fun getContacts(scannerId:String) {
        mUserModel.getContacts(
            scannerId,
            onSuccess = {
                mView?.showContacts(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun getUserId(): String {
        return mAuthModel.getUserId()
    }

    override fun onTapGroupItem() {
        mView?.navigateToNewGroupScreen()
    }
}