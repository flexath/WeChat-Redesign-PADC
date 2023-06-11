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
import com.flexath.moments.data.vos.MessageVO
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.databinding.ActivityChatDetailBinding
import com.flexath.moments.mvp.impls.ChatDetailPresenterImpl
import com.flexath.moments.mvp.interfaces.ChatDetailPresenter
import com.flexath.moments.mvp.views.ChatDetailView
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Suppress("DEPRECATION")
class ChatDetailActivity : AppCompatActivity(), ChatDetailView {

    private lateinit var binding: ActivityChatDetailBinding

    // Presenters
    private lateinit var mPresenter: ChatDetailPresenter

    // Adapters
    private lateinit var mAdapter: ChatDetailAdapter

    // General
    private var mUserName: String = ""
    private var mUserProfileImage: String = ""
    private var mReceiverId = ""
    private var timeStamp = 0L
    private var mFile = ""
    private var REQUEST_CODE_GALLERY = 0

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

        mPresenter.onUIReady( this)

        mPresenter.getMessages(mPresenter.getUserId(), mReceiverId)
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
            timeStamp = System.currentTimeMillis()
            Log.i("FileStringFile",mFile)
            if (message.isNotEmpty() || mFile.isNotEmpty()) {



                mPresenter.sendMessage(
                    mPresenter.getUserId(),
                    mReceiverId,
                    timeStamp,
                    MessageVO(
                        userId = mPresenter.getUserId(),
                        userName = mUserName,
                        userProfileImage = mUserProfileImage,
                        timeStamp = timeStamp,
                        file = mFile,
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
                        timeStamp = timeStamp,
                        file = mFile,
                        message = message
                    )
                )
            }
            binding.etSendMessageChatDetail.text?.clear()
        }

        binding.btnGetImageChatDetail.setOnClickListener {
            mPresenter.onTapGetImageButton()
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

            if (mPresenter.getUserId() == user.userId) {
                mUserName = user.userName
                mUserProfileImage = user.imageUrl
            }
        }
    }

    override fun showMessages(messageList: List<MessageVO>) {
        mAdapter.setNewData(mPresenter.getUserId(), messageList)
        binding.rvConversation.scrollToPosition(messageList.size - 1)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            val filePath = data.data

            Toast.makeText(this,"You choose a photo",Toast.LENGTH_SHORT).show()

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
        mFile = file
        Log.i("FIleString",mFile)
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}