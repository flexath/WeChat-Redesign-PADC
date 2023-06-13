package com.flexath.moments.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.flexath.moments.adapters.NewMomentImageAdapter
import com.flexath.moments.data.vos.MomentVO
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.databinding.ActivityNewMomentBinding
import com.flexath.moments.databinding.BottoomSheetDialogChooseImageBinding
import com.flexath.moments.mvp.impls.NewMomentPresenterImpl
import com.flexath.moments.mvp.interfaces.NewMomentPresenter
import com.flexath.moments.mvp.views.NewMomentView
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID

class NewMomentActivity : AppCompatActivity(), NewMomentView {

    private lateinit var binding: ActivityNewMomentBinding

    // Adapter
    private lateinit var mAdapter: NewMomentImageAdapter

    // Presenter
    private lateinit var mPresenter: NewMomentPresenter

    // General
    private val REQUEST_CODE_GALLERY = 0
    private val REQUEST_IMAGE_CAPTURE = 1
    private var userName: String = ""
    private var userProfileImage: String = ""
    private var userId:String = ""
    private lateinit var dialog:BottomSheetDialog

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, NewMomentActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMomentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPresenter()

        setUpListeners()
        setUpRecyclerView()

        mPresenter.onUIReady( this)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[NewMomentPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {
        binding.btnBackNewMoment.setOnClickListener {
            mPresenter.onTapBackButton()
        }

        binding.btnCreateNewMoment.setOnClickListener {
            mPresenter.onTapCreateButton(getMomentPost())
            mPresenter.clearMomentImages()
            finish()
        }
    }

    private fun getMomentPost(): MomentVO {
        val caption = binding.etPostNewMoment.text.toString()
        return MomentVO(
            System.currentTimeMillis().toString(),
            userId,
            userName,
            userProfileImage,
            caption,
            mPresenter.getMomentImages().dropLast(1)
        )
    }

    private fun setUpRecyclerView() {
        mAdapter = NewMomentImageAdapter(mPresenter)
        binding.rvBackground.adapter = mAdapter
        binding.rvBackground.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun navigateToPreviousScreen() {
        finish()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ((requestCode == REQUEST_CODE_GALLERY || requestCode == REQUEST_IMAGE_CAPTURE) && resultCode == Activity.RESULT_OK) {
//            if (data == null || data.data == null) {
//                return
//            }

            val filePath = data?.data

            if(requestCode == REQUEST_IMAGE_CAPTURE) {
                Toast.makeText(this, "You take a photo", Toast.LENGTH_SHORT).show()
                val imageBitmap = data?.extras?.get("data") as Bitmap
                mPresenter.createMomentImages(imageBitmap)

                val bitmap: Bitmap = imageBitmap
                val byteArrayOutputStream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
                val byteArray: ByteArray = byteArrayOutputStream.toByteArray()

                val file = File(applicationContext.cacheDir, "img${UUID.randomUUID()}.jpg")
                file.createNewFile()

                val fileOutputStream = FileOutputStream(file)
                fileOutputStream.write(byteArray)
                fileOutputStream.flush()
                fileOutputStream.close()
                val uri: Uri = FileProvider.getUriForFile(applicationContext, applicationContext.packageName + ".provider", file)
                mAdapter.setNewData(uri.toString())
                dialog.dismiss()
                return
            }

            mAdapter.setNewData(filePath.toString())

            try {
                filePath?.let { fileUrl ->
                    if (Build.VERSION.SDK_INT >= 29) {
                        val source = ImageDecoder.createSource(this.contentResolver, fileUrl)
                        val bitmapImage = ImageDecoder.decodeBitmap(source)
                        mPresenter.createMomentImages(bitmapImage)
                    } else {
                        val bitmapImage = MediaStore.Images.Media.getBitmap(
                            applicationContext.contentResolver, fileUrl
                        )
                        mPresenter.createMomentImages(bitmapImage)
                    }
                }
                dialog.dismiss()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun showGallery() {

        dialog = BottomSheetDialog(this)

        val dialogBinding = BottoomSheetDialogChooseImageBinding.inflate(layoutInflater)

        dialog.setContentView(dialogBinding.root)
        dialog.setCancelable(true)

        dialogBinding.btnTakePhotoRegister.setOnClickListener {
            openCamera()
        }

        dialogBinding.btnChooseFromGalleryRegister.setOnClickListener {
            chooseImageFromGallery()
        }

        dialogBinding.btnCancelBottomSheetDialog.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun chooseImageFromGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select Upload Image"),
            REQUEST_CODE_GALLERY
        )
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(
                Intent.createChooser(intent, "Select Upload Image"),
                REQUEST_IMAGE_CAPTURE
            )
        }
    }

    override fun showUserInformation(userList: List<UserVO>) {
        for (user in userList) {
            if (mPresenter.getUserId() == user.userId) {

                userId = user.userId
                userName = user.userName
                userProfileImage = user.imageUrl

                binding.tvUserNameNewMoment.text = user.userName

                Glide.with(this)
                    .load(user.imageUrl)
                    .into(binding.ivProfileImageNewMoment)
            }
        }
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

}