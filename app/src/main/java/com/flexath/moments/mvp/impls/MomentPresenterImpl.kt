package com.flexath.moments.mvp.impls

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.flexath.moments.data.models.AuthenticationModel
import com.flexath.moments.data.models.AuthenticationModelImpl
import com.flexath.moments.data.models.MomentModel
import com.flexath.moments.data.models.MomentModelImpl
import com.flexath.moments.data.vos.MomentVO
import com.flexath.moments.mvp.interfaces.MomentPresenter
import com.flexath.moments.mvp.views.MomentView

class MomentPresenterImpl : MomentPresenter , ViewModel() {

    private var mView:MomentView? = null
    private val mMomentModel:MomentModel = MomentModelImpl
    private val mAuthModel:AuthenticationModel = AuthenticationModelImpl

    override fun initPresenter(view: MomentView) {
        mView = view
    }

    override fun onUIReady(lifecycleOwner: LifecycleOwner) {
        mMomentModel.getMoments(
            onSuccess =  {
                mView?.showMoments(it)
            },
            onFailure = {
                mView?.showError(it)
            }
        )
    }

    override fun onTapBookmarkButton(id: String,isBookmarked:Boolean) {
        mView?.getMomentIsBookmarked(id,isBookmarked)
    }

    override fun onTapOptionButton() {
        mView?.showOptionDialogBox()
    }

    override fun onTapAddMomentButton() {
        mView?.navigateToNewMomentScreen()
    }

    override fun createMoment(moment: MomentVO) {
        mMomentModel.createMoment(moment)
    }

    override fun getUserId(): String {
        return mAuthModel.getUserId()
    }
}