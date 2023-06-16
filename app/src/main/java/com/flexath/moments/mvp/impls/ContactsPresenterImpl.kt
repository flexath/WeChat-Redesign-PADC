package com.flexath.moments.mvp.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.moments.data.models.AuthenticationModel
import com.flexath.moments.data.models.AuthenticationModelImpl
import com.flexath.moments.data.models.ChatModel
import com.flexath.moments.data.models.ChatModelImpl
import com.flexath.moments.data.models.UserModel
import com.flexath.moments.data.models.UserModelImpl
import com.flexath.moments.mvp.interfaces.ContactsPresenter
import com.flexath.moments.mvp.views.ContactsView

class ContactsPresenterImpl : ContactsPresenter , ViewModel() {

    private var mView:ContactsView? = null
    private var mUserModel:UserModel = UserModelImpl
    private var mAuthModel:AuthenticationModel = AuthenticationModelImpl
    private var mChatModel:ChatModel = ChatModelImpl

    override fun initPresenter(view: ContactsView) {
        mView = view
    }

    override fun onUIReady(lifecycleOwner: LifecycleOwner) {
        mChatModel.getGroups(
            onSuccess = {
                mView?.getGroupList(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapGroupItem(groupId: Long) {
        mView?.navigateToChatDetailScreenFromGroupItem(groupId.toString())
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

    override fun onTapAlphabetItem(position: Int) {}

    override fun onTapChatItem(userId: String) {
        mView?.navigateToChatDetailScreen(userId)
    }

    override fun onTapCheckbox(userId: String, isCheck: Boolean) {}


}