package com.flexath.moments.mvp.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.moments.mvp.interfaces.MomentPresenter
import com.flexath.moments.mvp.views.MomentView

class MomentPresenterImpl : MomentPresenter , ViewModel() {

    private var mView:MomentView? = null

    override fun initPresenter(view: MomentView) {
        mView = view
    }

    override fun onUIReady(context: Context, lifecycleOwner: LifecycleOwner) {

    }

    override fun onTapAddMomentButton() {
        mView?.navigateToNewMomentScreen()
    }
}