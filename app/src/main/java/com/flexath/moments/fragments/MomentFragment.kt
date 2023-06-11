package com.flexath.moments.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.flexath.moments.activities.NewMomentActivity
import com.flexath.moments.data.vos.MomentVO
import com.flexath.moments.databinding.FragmentMomentBinding
import com.flexath.moments.mvp.impls.MomentPresenterImpl
import com.flexath.moments.mvp.interfaces.MomentPresenter
import com.flexath.moments.mvp.views.MomentView
import com.flexath.moments.views.viewpods.MomentViewPod

class MomentFragment : Fragment(), MomentView {

    private lateinit var binding: FragmentMomentBinding

    // Presenter
    private lateinit var mPresenter: MomentPresenter

    // ViewPods
    private lateinit var mViewpod: MomentViewPod

    // Generals
    private var mMomentList: List<MomentVO> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMomentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenter()
        setUpViewPods()

        setUpListeners()

        mPresenter.onUIReady( this)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MomentPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpViewPods() {
        mViewpod = binding.vpMoment.root
        mViewpod.setDelegate(mPresenter)
    }

    private fun setUpListeners() {
        binding.btnAddMoment.setOnClickListener {
            mPresenter.onTapAddMomentButton()
        }
    }

    override fun navigateToNewMomentScreen() {
        startActivity(NewMomentActivity.newIntent(requireActivity()))
    }

    override fun showMoments(momentList: List<MomentVO>) {
        mMomentList = momentList
        mViewpod.setNewData(momentList.reversed())
    }

    override fun getMomentIsBookmarked(id: String, isBookmarked: Boolean) {
        for (moment in mMomentList) {
            if (id == moment.id) {
                if (isBookmarked) {
                    moment.isBookmarked = true
                    mPresenter.createMoment(moment)
                    break
                } else {
                    moment.isBookmarked = false
                    mPresenter.createMoment(moment)
                    break
                }
            }
        }
        mViewpod.setNewData(mMomentList)
    }

    override fun showOptionDialogBox() {}

    override fun showError(error: String) {
        Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show()
    }

}