package com.flexath.moments.mvp.interfaces

import com.flexath.moments.mvp.views.NewMomentView

interface NewMomentPresenter : BasePresenter<NewMomentView> {
    fun onTapBackButton()
    fun onTapCreateButton()
}