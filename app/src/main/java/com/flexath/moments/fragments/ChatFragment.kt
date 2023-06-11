package com.flexath.moments.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexath.moments.activities.ChatDetailActivity
import com.flexath.moments.adapters.ActiveChatAdapter
import com.flexath.moments.adapters.ChatAdapter
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.databinding.FragmentChatBinding
import com.flexath.moments.mvp.impls.ChatPresenterImpl
import com.flexath.moments.mvp.interfaces.ChatPresenter
import com.flexath.moments.mvp.views.ChatView

class ChatFragment : Fragment(), ChatView {

    private lateinit var binding: FragmentChatBinding

    // Adapters
    private lateinit var mActiveChatAdapter: ActiveChatAdapter
    private lateinit var mChatAdapter: ChatAdapter

    // Presenters
    private lateinit var mPresenter: ChatPresenter

    // General
    private var mUserList: List<UserVO> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpPresenters()

        setUpActiveChatRecyclerView()
        setUpChatRecyclerView()

        mPresenter.onUIReady(this)

        val currentUserId = mPresenter.getUserId()
        mPresenter.getContacts(currentUserId)
        mPresenter.getChatHistoryUserId(currentUserId)
    }

    private fun setUpPresenters() {
        mPresenter = ViewModelProvider(this)[ChatPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpActiveChatRecyclerView() {
        mActiveChatAdapter = ActiveChatAdapter()
        binding.rvActiveChats.adapter = mActiveChatAdapter
        binding.rvActiveChats.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpChatRecyclerView() {
        mChatAdapter = ChatAdapter(mPresenter)
        binding.rvChats.adapter = mChatAdapter
        binding.rvChats.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun showContacts(contactList: List<UserVO>) {
        mActiveChatAdapter.setNewData(contactList)
    }

    override fun navigateToChatDetailScreen(userId: String) {
        startActivity(ChatDetailActivity.newIntent(requireActivity(), userId))
    }

    override fun showUserId(userIdList: List<String>) {
        val chatUserList = arrayListOf<UserVO>()
        for(userId in userIdList) {
            for(user in mUserList) {
                if(userId == user.userId) {
                    chatUserList.add(user)
                    break
                }
            }
        }
        mChatAdapter.setNewData(chatUserList)
    }

    override fun getUsers(userList: List<UserVO>) {
        mUserList = userList
    }

    override fun showError(error: String) {
        Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show()
    }

}