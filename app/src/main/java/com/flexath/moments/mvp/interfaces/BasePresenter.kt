package com.flexath.moments.mvp.interfaces

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.flexath.moments.mvp.views.BaseView

interface BasePresenter<V : BaseView> {

    fun initPresenter(view: V)
    fun onUIReady(context: Context, lifecycleOwner: LifecycleOwner)
}