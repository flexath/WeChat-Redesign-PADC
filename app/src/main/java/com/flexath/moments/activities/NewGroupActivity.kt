package com.flexath.moments.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexath.moments.adapters.NewMemberGroupAdapter
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.databinding.ActivityNewGroupBinding
import com.flexath.moments.databinding.BottoomSheetDialogChooseImageBinding
import com.flexath.moments.mvp.impls.NewGroupPresenterImpl
import com.flexath.moments.mvp.interfaces.NewGroupPresenter
import com.flexath.moments.mvp.views.NewGroupView
import com.flexath.moments.views.viewpods.ContactsViewPod
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.IOException

@Suppress("DEPRECATION")
class NewGroupActivity : AppCompatActivity(), NewGroupView {

    private lateinit var binding: ActivityNewGroupBinding

    // Adapters
    private lateinit var mAdapter: NewMemberGroupAdapter

    // Presenters
    private lateinit var mPresenter: NewGroupPresenter

    // ViewPods
    private lateinit var mViewPod: ContactsViewPod

    // General
    private var mMemberList: ArrayList<UserVO> = arrayListOf()
    private var mUserList: List<UserVO> = listOf()
    private val REQUEST_CODE_GALLERY = 0
    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var dialog: BottomSheetDialog
    private var mGroupCoverImageUrl: String = ""

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, NewGroupActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPresenter()
        setUpViewPods()
        setUpRecyclerView()

        setUpListeners()
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[NewGroupPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {

        binding.btnCreateNewGroup.setOnClickListener {
            val mUserIdList = arrayListOf<String>()
            val groupName = binding.etGroupNameNewGroup.text.toString()
            for (member in mMemberList) {
                mUserIdList.add(member.userId)
            }
            mUserIdList.add(mPresenter.getUserId())
            if (groupName.isNotEmpty() && mGroupCoverImageUrl.isNotEmpty()) {
                mPresenter.onTapCreateButton(
                    System.currentTimeMillis(),
                    groupName,
                    mUserIdList.toList(),
                    mGroupCoverImageUrl
                )
                finish()
            }
        }

        binding.etSearchContactsNewGroup.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    searchContacts(it)
                }
                return true
            }
        })

        dialog = BottomSheetDialog(this)
        binding.ivProfileImageNewGroup.setOnClickListener {
            val dialogBinding = BottoomSheetDialogChooseImageBinding.inflate(layoutInflater)

            dialog.setContentView(dialogBinding.root)
            dialog.setCancelable(true)

            dialogBinding.btnTakePhotoRegister.setOnClickListener {
                mPresenter.onTapOpenCameraButton()
            }

            dialogBinding.btnChooseFromGalleryRegister.setOnClickListener {
                mPresenter.onTapProfileImage()
            }

            dialogBinding.btnCancelBottomSheetDialog.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun searchContacts(newText: String) {
        val contactList = arrayListOf<UserVO>()
        Log.i("UserListSize", mUserList.size.toString())
        Log.i("UserListSizeText", newText)
        for (contact in mUserList) {
            if (contact.userName.contains(newText)) {
                contactList.add(contact)
            }
        }
        mViewPod.setNewData(
            getAlphabetList(getNameFirstLetterList(contactList)),
            contactList,
            true
        )
    }

    private fun setUpViewPods() {
        mViewPod = binding.vpGroup.root
        mViewPod.setDelegate(mPresenter, mPresenter)

        mPresenter.getContacts(mPresenter.getUserId())
    }

    private fun setUpRecyclerView() {
        mAdapter = NewMemberGroupAdapter()
        binding.rvMemberNewGroup.adapter = mAdapter
        binding.rvMemberNewGroup.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ((requestCode == REQUEST_CODE_GALLERY || requestCode == REQUEST_IMAGE_CAPTURE) && resultCode == Activity.RESULT_OK) {
            val filePath = data?.data

            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Toast.makeText(this, "You take a photo", Toast.LENGTH_SHORT).show()
                val imageBitmap = data?.extras?.get("data") as Bitmap
                mPresenter.uploadGroupCoverPhoto(System.currentTimeMillis(), imageBitmap)
                binding.ivProfileImageNewGroup.setImageBitmap(imageBitmap)
                dialog.dismiss()
                return
            }

            Toast.makeText(this, "You choose a photo", Toast.LENGTH_SHORT).show()

            try {
                filePath?.let { fileUrl ->
                    if (Build.VERSION.SDK_INT >= 29) {
                        val source = ImageDecoder.createSource(this.contentResolver, fileUrl)
                        val bitmapImage = ImageDecoder.decodeBitmap(source)
                        mPresenter.uploadGroupCoverPhoto(System.currentTimeMillis(), bitmapImage)
                        binding.ivProfileImageNewGroup.setImageBitmap(bitmapImage)
                    } else {
                        val bitmapImage = MediaStore.Images.Media.getBitmap(
                            applicationContext.contentResolver, fileUrl
                        )
                        mPresenter.uploadGroupCoverPhoto(System.currentTimeMillis(), bitmapImage)
                        binding.ivProfileImageNewGroup.setImageBitmap(bitmapImage)
                    }
                }
                dialog.dismiss()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun getGroupCoverImageUrl(imageUrl: String) {
        mGroupCoverImageUrl = imageUrl
        Log.i("NewGroupCoverUrl",mGroupCoverImageUrl)
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

    @SuppressLint("QueryPermissionsNeeded")
    override fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(
                Intent.createChooser(intent, "Select Upload Image"),
                REQUEST_IMAGE_CAPTURE
            )
        }
    }

    private fun getAlphabetList(nameList: List<String>): List<Char> {
        val nameMapList = nameList.groupBy { it[0] }
        val alphabetList = arrayListOf<Char>()
        for (key in nameMapList.keys) {
            alphabetList.add(key)
        }
        return alphabetList.sorted()
    }

    override fun showContacts(userList: List<UserVO>) {
        mUserList = userList
        mViewPod.setNewData(getAlphabetList(getNameFirstLetterList(userList)), userList, true)
    }

    private fun getNameFirstLetterList(contactList: List<UserVO>): ArrayList<String> {
        val nameList = arrayListOf<String>()
        for (contact in contactList) {
            nameList.add(0, contact.userName)
        }
        return nameList
    }

    override fun navigateToChatDetailScreen(userId: String) {
        startActivity(ChatDetailActivity.newIntent(this, userId, ""))
    }

    override fun addUserToGroup(userId: String, isCheck: Boolean) {

        if (isCheck) {
            for (user in mUserList) {
                if (userId == user.userId) {
                    mMemberList.add(user)
                }
            }
        } else {
            for (user in mUserList) {
                if (userId == user.userId) {
                    mMemberList.remove(user)
                }
            }
        }
        mAdapter.setNewData(mMemberList)
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}