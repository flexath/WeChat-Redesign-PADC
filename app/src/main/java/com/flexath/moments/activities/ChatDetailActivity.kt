package com.flexath.moments.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.flexath.moments.adapters.ChatDetailAdapter
import com.flexath.moments.adapters.ImageChatDetailAdapter
import com.flexath.moments.data.vos.GroupVO
import com.flexath.moments.data.vos.MessageVO
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.databinding.ActivityChatDetailBinding
import com.flexath.moments.mvp.impls.ChatDetailPresenterImpl
import com.flexath.moments.mvp.interfaces.ChatDetailPresenter
import com.flexath.moments.mvp.views.ChatDetailView
import java.io.IOException

@Suppress("DEPRECATION")
class ChatDetailActivity : AppCompatActivity(), ChatDetailView {

    private lateinit var binding: ActivityChatDetailBinding

    // Presenters
    private lateinit var mPresenter: ChatDetailPresenter

    // Adapters
    private lateinit var mAdapter: ChatDetailAdapter
    private lateinit var mImageAdapter: ImageChatDetailAdapter

    // General
    private var mUserName: String = ""
    private var mUserProfileImage: String = ""
    private var mReceiverId = ""
    private var mGroupId = ""
    private var mGroupName = ""
//    private var timeStamp = 0L
    private var REQUEST_CODE_GALLERY = 0
    private var mImageList: ArrayList<String> = arrayListOf()

    companion object {
        private const val EXTRA_USER_ID = "UserId"
        private const val EXTRA_GROUP_ID = "GroupId"
        fun newIntent(context: Context, userId: String, groupId: String): Intent {
            val intent = Intent(context, ChatDetailActivity::class.java)
            intent.putExtra(EXTRA_USER_ID, userId)
            intent.putExtra(EXTRA_GROUP_ID, groupId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPresenters()
        setUpRecyclerView()
        setUpImageRecyclerView()

        mReceiverId = intent?.extras?.getString(EXTRA_USER_ID, "") ?: ""
        mGroupId = intent?.extras?.getString(EXTRA_GROUP_ID, "") ?: ""

        setUpListeners()

        mPresenter.onUIReady(this)

        if (mGroupId.isEmpty()) {
            mPresenter.getMessages(mPresenter.getUserId(), mReceiverId)
        } else {
            mPresenter.getGroupMessages(mGroupId.toLong())
        }
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

    private fun setUpImageRecyclerView() {
        mImageAdapter = ImageChatDetailAdapter()
        binding.rvImages.adapter = mImageAdapter
        binding.rvImages.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setUpListeners() {

        binding.btnSendMessage.setOnClickListener {
            val message = binding.etSendMessageChatDetail.text.toString()

            if (message.isNotEmpty() || mImageList.isNotEmpty()) {

                if (mGroupId.isEmpty()) {
                    sendPrivateChatMessage(message = message)
                } else {
                    sendGroupChatMessage(message = message)
                }
            }
            mImageList.clear()
            mImageAdapter.setNewData(mImageList)
            binding.etSendMessageChatDetail.text?.clear()
        }

        binding.btnGetImageChatDetail.setOnClickListener {
            mPresenter.onTapGetImageButton()
        }
    }

    private fun sendPrivateChatMessage(message: String) {
        Log.i("ImageCount",mImageList.size.toString())
        if (mImageAdapter.itemCount > 0 && message.isEmpty()) {
            for (image in mImageList) {
                val timeStamp = System.currentTimeMillis()
                mPresenter.sendMessage(mPresenter.getUserId(), mReceiverId, timeStamp, MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = timeStamp, file = image, message = "", groupName = ""))
                mPresenter.sendMessage(mReceiverId, mPresenter.getUserId(), timeStamp, MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = timeStamp, file = image, message = "", groupName = ""))
            }
        } else if (message.isNotEmpty() && mImageList.isEmpty()) {
            val timeStamp = System.currentTimeMillis()
            mPresenter.sendMessage(mPresenter.getUserId(), mReceiverId, timeStamp, MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = timeStamp, file = "", message = message, groupName = ""))
            mPresenter.sendMessage(mReceiverId, mPresenter.getUserId(), timeStamp, MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = timeStamp, file = "", message = message, groupName = ""))
        } else {
            val newTimeStamp = System.currentTimeMillis()
            mPresenter.sendMessage(mPresenter.getUserId(), mReceiverId, newTimeStamp, MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = newTimeStamp, file = "", message = message, groupName = ""))
            mPresenter.sendMessage(mReceiverId, mPresenter.getUserId(), newTimeStamp, MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = newTimeStamp, file = "", message = message, groupName = ""))

            for (image in mImageList) {
                val timeStamp = System.currentTimeMillis()
                mPresenter.sendMessage(mPresenter.getUserId(), mReceiverId, timeStamp, MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = timeStamp, file = image, message = "", groupName = ""))
                mPresenter.sendMessage(mReceiverId, mPresenter.getUserId(), timeStamp, MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = timeStamp, file = image, message = "", groupName = ""))
            }
        }
    }

    private fun sendGroupChatMessage(message: String) {
        if (mImageAdapter.itemCount > 0 && message.isEmpty()) {
            for (image in mImageList) {
                val timeStamp = System.currentTimeMillis()
                mPresenter.sendGroupMessage(mGroupId.toLong(),timeStamp, MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = timeStamp, file = image, message = "", groupName = mGroupName))
            }
        } else if (message.isNotEmpty() && mImageList.isEmpty())  {
            val timeStamp = System.currentTimeMillis()
            mPresenter.sendGroupMessage(mGroupId.toLong(),timeStamp, MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = timeStamp, file = "", message = message, groupName = mGroupName))
        } else {
            val newTimeStamp = System.currentTimeMillis()
            mPresenter.sendGroupMessage(mGroupId.toLong(),newTimeStamp, MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = newTimeStamp, file = "", message = message, groupName = mGroupName))

            for (image in mImageList) {
                val timeStamp = System.currentTimeMillis()
                mPresenter.sendGroupMessage(mGroupId.toLong(),timeStamp,
                    MessageVO(userId = mPresenter.getUserId(), userName = mUserName, userProfileImage = mUserProfileImage, timeStamp = timeStamp, file = image, message = "", groupName = mGroupName))
            }
        }
    }

    override fun showMessages(messageList: List<MessageVO>) {
        Log.i("WhoIsIt", "User")
        mAdapter.setNewData(mPresenter.getUserId(), messageList)
        binding.rvConversation.scrollToPosition(messageList.size - 1)
    }

    override fun showGroupMessages(messageList: List<MessageVO>) {
        Log.i("WhoIsIt", "Group")
        mAdapter.setNewData(mPresenter.getUserId(), messageList)
        binding.rvConversation.scrollToPosition(messageList.size - 1)
    }

    override fun showUsers(userList: List<UserVO>) {
        for (user in userList) {
            if (mReceiverId == user.userId) {
                binding.tvNameChatDetail.text = user.userName

                Glide.with(this)
                    .load(user.imageUrl)
                    .into(binding.ivProfileImageChatDetail)
            }

            if (mPresenter.getUserId() == user.userId) {
                mUserName = user.userName
                mUserProfileImage = user.imageUrl
            }
        }
    }

    override fun getGroups(groupList: List<GroupVO>) {
        for (group in groupList) {
            if (mGroupId == group.id.toString()) {
                binding.tvNameChatDetail.text = group.name
                mGroupName = group.name
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            val filePath = data.data

            Toast.makeText(this, "You choose a photo", Toast.LENGTH_SHORT).show()

            try {
                filePath?.let { fileUrl ->
                    if (Build.VERSION.SDK_INT >= 29) {
                        val source = ImageDecoder.createSource(this.contentResolver, fileUrl)
                        val bitmapImage = ImageDecoder.decodeBitmap(source)
                        mPresenter.uploadAndSendImage(bitmapImage)
                    } else {
                        val bitmapImage = MediaStore.Images.Media.getBitmap(
                            applicationContext.contentResolver, fileUrl
                        )
                        mPresenter.uploadAndSendImage(bitmapImage)
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun showGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Upload Image"),
            REQUEST_CODE_GALLERY
        )
    }

    override fun getImageUrlForFile(file: String) {
        mImageList.add(file)
        mImageAdapter.setNewData(mImageList.toList())
    }


    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}