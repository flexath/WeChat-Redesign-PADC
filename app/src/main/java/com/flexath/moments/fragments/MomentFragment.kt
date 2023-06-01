package com.flexath.moments.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flexath.moments.R
import com.flexath.moments.activities.NewMomentActivity
import com.flexath.moments.databinding.FragmentChatBinding
import com.flexath.moments.databinding.FragmentMomentBinding

class MomentFragment : Fragment() {

    private lateinit var binding:FragmentMomentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMomentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpListeners()
    }

    private fun setUpListeners() {
        binding.btnAddMomentChat.setOnClickListener {
            startActivity(NewMomentActivity.newIntent(requireActivity()))
        }
    }

}