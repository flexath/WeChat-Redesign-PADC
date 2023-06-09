package com.flexath.moments.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.flexath.moments.adapters.ChatDetailAdapter
import com.flexath.moments.data.vos.MessageVO
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.databinding.ActivityChatDetailBinding
import com.flexath.moments.mvp.impls.ChatDetailPresenterImpl
import com.flexath.moments.mvp.interfaces.ChatDetailPresenter
import com.flexath.moments.mvp.views.ChatDetailView

class ChatDetailActivity : AppCompatActivity(), ChatDetailView {

    private lateinit var binding: ActivityChatDetailBinding

    // Presenters
    private lateinit var mPresenter: ChatDetailPresenter

    // Adapters
    private lateinit var mAdapter:ChatDetailAdapter

    // General
    private var mUserName: String = ""
    private var mUserProfileImage: String = ""
    private var mReceiverId = ""

    companion object {
        private const val EXTRA_USER_ID = "UserId"
        fun newIntent(context: Context, userId: String): Intent {
            val intent = Intent(context, ChatDetailActivity::class.java)
            intent.putExtra(EXTRA_USER_ID, userId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPresenters()
        setUpRecyclerView()
        mReceiverId = intent?.extras?.getString(EXTRA_USER_ID, "") ?: ""
        setUpListeners()

        mPresenter.onUIReady(this,this)

        mPresenter.getMessages(mPresenter.getUserId(),mReceiverId)
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setUpPresenters() {
        mPresenter = ViewModelProvider(this)[ChatDetailPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpRecyclerView() {
        mAdapter = ChatDetailAdapter()
        binding.rvConversation.adapter = mAdapter
        binding.rvConversation.layoutManager = LinearLayoutManager(this)
    }

    private fun setUpListeners() {

        binding.btnSendMessage.setOnClickListener {
            val message = binding.etSendMessageChatDetail.text.toString()
            val timeStamp = System.currentTimeMillis()
            if(message.isNotEmpty()) {
                mPresenter.sendMessage(
                    mPresenter.getUserId(),
                    mReceiverId,
                    timeStamp,
                    MessageVO(
                        userId = mPresenter.getUserId(),
                        userName = mUserName,
                        userProfileImage = mUserProfileImage,
                        timeStamp =  timeStamp,
                        file = "",
                        message = message
                    )
                )

                mPresenter.sendMessage(
                    mReceiverId,
                    mPresenter.getUserId(),
                    timeStamp,
                    MessageVO(
                        userId = mPresenter.getUserId(),
                        userName = mUserName,
                        userProfileImage = mUserProfileImage,
                        timeStamp =  timeStamp,
                        file = "",
                        message = message
                    )
                )
            }
            binding.etSendMessageChatDetail.text?.clear()
        }
    }

    override fun showUsers(userList: List<UserVO>) {
        for (user in userList) {
            if (mReceiverId == user.userId) {
                binding.tvNameChatDetail.text = user.userName

                Glide.with(this)
                    .load(user.imageUrl)
                    .into(binding.ivProfileImageChatDetail)
            }

            if(mPresenter.getUserId() == user.userId) {
                mUserName = user.userName
                mUserProfileImage = user.imageUrl
            }
        }
    }

    override fun showMessages(messageList: List<MessageVO>) {
        mAdapter.setNewData(mPresenter.getUserId(),messageList)
        binding.rvConversation.scrollToPosition(messageList.size - 1)
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}