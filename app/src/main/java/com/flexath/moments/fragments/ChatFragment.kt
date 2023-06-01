package com.flexath.moments.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexath.moments.activities.ChatDetailActivity
import com.flexath.moments.adapters.ActiveChatAdapter
import com.flexath.moments.adapters.ChatAdapter
import com.flexath.moments.databinding.FragmentChatBinding
import com.flexath.moments.delegates.ChatItemActionDelegate

class ChatFragment : Fragment(),ChatItemActionDelegate {

    private lateinit var binding:FragmentChatBinding
    private lateinit var mActiveChatAdapter:ActiveChatAdapter
    private lateinit var mChatAdapter:ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpActiveChatRecyclerView()
        setUpChatRecyclerView()
    }

    private fun setUpActiveChatRecyclerView() {
        mActiveChatAdapter = ActiveChatAdapter()
        binding.rvActiveChats.adapter = mActiveChatAdapter
        binding.rvActiveChats.layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
    }

    private fun setUpChatRecyclerView() {
        mChatAdapter = ChatAdapter(this)
        binding.rvChats.adapter = mChatAdapter
        binding.rvChats.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun onTapChat() {
        startActivity(ChatDetailActivity.newIntent(requireActivity()))
    }

}