package com.flexath.moments.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.flexath.moments.R
import com.flexath.moments.activities.NewMomentActivity
import com.flexath.moments.databinding.FragmentChatBinding
import com.flexath.moments.databinding.FragmentMomentBinding
import com.flexath.moments.mvp.impls.MomentPresenterImpl
import com.flexath.moments.mvp.interfaces.MomentPresenter
import com.flexath.moments.mvp.views.MomentView

class MomentFragment : Fragment() , MomentView {

    private lateinit var binding:FragmentMomentBinding

    // Presenter
    private lateinit var mPresenter:MomentPresenter

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

        setUpListeners()
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[MomentPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {
        binding.btnAddMoment.setOnClickListener {
            mPresenter.onTapAddMomentButton()
        }
    }

    override fun navigateToNewMomentScreen() {
        startActivity(NewMomentActivity.newIntent(requireActivity()))
    }

    override fun showError(error: String) {
        Toast.makeText(requireActivity(),error,Toast.LENGTH_SHORT).show()
    }

}