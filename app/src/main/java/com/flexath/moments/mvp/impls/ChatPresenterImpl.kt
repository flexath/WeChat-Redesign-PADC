package com.flexath.moments.mvp.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.moments.mvp.interfaces.ChatPresenter
import com.flexath.moments.mvp.views.ChatView

class ChatPresenterImpl : ChatPresenter , ViewModel() {

    private var mView:ChatView? = null
    override fun initPresenter(view: ChatView) {
        mView = view
    }

    override fun onUIReady(context: Context, lifecycleOwner: LifecycleOwner) {

    }

}