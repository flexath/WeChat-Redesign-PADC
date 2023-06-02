package com.flexath.moments.mvp.interfaces

import com.flexath.moments.mvp.views.MomentView

interface MomentPresenter  : BasePresenter<MomentView> {
    fun onTapAddMomentButton()
}