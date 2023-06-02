package com.flexath.moments.mvp.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.moments.mvp.interfaces.ContactsPresenter
import com.flexath.moments.mvp.views.ContactsView

class ContactsPresenterImpl : ContactsPresenter , ViewModel() {

    private var mView:ContactsView? = null

    override fun initPresenter(view: ContactsView) {
        mView = view
    }

    override fun onUIReady(context: Context, lifecycleOwner: LifecycleOwner) {

    }

    override fun onTapAddNewContactButton() {
        mView?.navigateToNewContactScreen()
    }

    override fun onTapGroupItem() {
        mView?.navigateToNewGroupScreen()
    }
}