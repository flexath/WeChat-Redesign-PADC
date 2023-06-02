package com.flexath.moments.mvp.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.moments.mvp.interfaces.NewMomentPresenter
import com.flexath.moments.mvp.views.NewMomentView

class NewMomentPresenterImpl : NewMomentPresenter , ViewModel() {

    private var mView:NewMomentView? = null

    override fun initPresenter(view: NewMomentView) {
        mView = view
    }

    override fun onUIReady(context: Context, lifecycleOwner: LifecycleOwner) {

    }

    override fun onTapBackButton() {
        mView?.navigateToPreviousScreen()
    }

    override fun onTapCreateButton() {
        mView?.createNewMoment()
    }

}