package com.flexath.moments.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.flexath.moments.adapters.NewMomentImageAdapter
import com.flexath.moments.data.vos.MomentVO
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.databinding.ActivityNewMomentBinding
import com.flexath.moments.mvp.impls.NewMomentPresenterImpl
import com.flexath.moments.mvp.interfaces.NewMomentPresenter
import com.flexath.moments.mvp.views.NewMomentView
import java.io.IOException

class NewMomentActivity : AppCompatActivity() , NewMomentView {

    private lateinit var binding:ActivityNewMomentBinding

    // Adapter
    private lateinit var mAdapter:NewMomentImageAdapter

    // Presenter
    private lateinit var mPresenter:NewMomentPresenter

    // General
    private val REQUEST_CODE_GALLERY = 300
    private var bitmap: Bitmap? = null

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

        mPresenter.onUIReady(this,this)
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
            mPresenter.onTapCreateButton()
        }
    }

    private fun setUpRecyclerView() {
        mAdapter = NewMomentImageAdapter(mPresenter)
        binding.rvBackground.adapter = mAdapter
        binding.rvBackground.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

//        binding.rvBackground.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                if (dy > 0 && binding.etPostNewMoment.isFocused) {
//                    binding.etPostNewMoment.clearFocus()
//                    hideKeyboard()
//                }
//            }
//        })

    }
    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.etPostNewMoment.windowToken, 0)
    }

    override fun createNewMoment() {

        finish()
    }

    override fun navigateToPreviousScreen() {
        finish()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            val filePath = data.data

            mAdapter.setNewData(filePath.toString())

            try {
                filePath?.let { fileUrl ->
                    if (Build.VERSION.SDK_INT >= 29) {
                        val source = ImageDecoder.createSource(this.contentResolver, fileUrl)
                        val bitmapImage = ImageDecoder.decodeBitmap(source)
                        bitmap = bitmapImage


                    } else {
                        val bitmapImage = MediaStore.Images.Media.getBitmap(
                            applicationContext.contentResolver, fileUrl
                        )
                        bitmap = bitmapImage


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
        startActivityForResult(Intent.createChooser(intent, "Select Upload Image"), REQUEST_CODE_GALLERY)
    }

    override fun showUserInformation(userList: List<UserVO>) {
        for(user in userList) {
            if(mPresenter.getUserId() == user.userId) {

                binding.tvUserNameNewMoment.text = user.userName

                Glide.with(this)
                    .load(user.imageUrl)
                    .into(binding.ivProfileImageNewMoment)
            }
        }
    }

    override fun showError(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
    }

}