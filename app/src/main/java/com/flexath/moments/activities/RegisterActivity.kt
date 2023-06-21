package com.flexath.moments.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.flexath.moments.R
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.databinding.ActivityRegisterBinding
import com.flexath.moments.databinding.BottoomSheetDialogChooseImageBinding
import com.flexath.moments.databinding.DialogRegisterationSuccessfulBinding
import com.flexath.moments.dialogs.RegisterSuccessfulDialog
import com.flexath.moments.mvp.impls.RegisterPresenterImpl
import com.flexath.moments.mvp.interfaces.RegisterPresenter
import com.flexath.moments.mvp.views.RegisterView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.messaging.FirebaseMessaging
import java.io.IOException
import java.util.Calendar

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity(), RegisterView {

    private lateinit var binding: ActivityRegisterBinding

    // Presenters
    private lateinit var mPresenter: RegisterPresenter

    // General
    private var bitmap:Bitmap? = null
    private val REQUEST_CODE_GALLERY = 0
    private val REQUEST_IMAGE_CAPTURE = 1
    private var day = ""
    private var month = ""
    private var year = ""
    private var gender = ""
    private lateinit var dialog:BottomSheetDialog
    private lateinit var fcmToken:String

    companion object {
        private const val EXTRA_PHONE_NUMBER = "PhoneNumber"
        private const val EXTRA_EMAIL = "EmailAddress"
        fun newIntent(context: Context, phoneNumber: String, email: String): Intent {
            val intent = Intent(context, RegisterActivity::class.java)
            intent.putExtra(EXTRA_PHONE_NUMBER, phoneNumber)
            intent.putExtra(EXTRA_EMAIL, email)
            return intent
        }
    }

    override fun onStart() {
        super.onStart()
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            Log.i("NewTokenFCM",it.result)
            fcmToken = it.result
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPresenter()

        setUpListeners()
        setUpDateOfBirthSpinners()
        setUpGenderRadioGroup()
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[RegisterPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {
        setUpYearSpinner()

        dialog = BottomSheetDialog(this)

        binding.btnSignUpRegister.setOnClickListener {
            bitmap?.let { bitmapImage ->
                mPresenter.onTapSignUpButton(getNewUserInformation(), bitmapImage)
            }
        }

        binding.btnBackRegister.setOnClickListener {
            mPresenter.onTapBackButton()
        }

        binding.ivProfileImageRegister.setOnClickListener {
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

    private fun setUpYearSpinner() {
        val years = arrayListOf("Year")
        val thisYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        for (year in 1900..thisYear) {
            years.add(year.toString())
        }

        val adapter = ArrayAdapter(
            this,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            years
        )
        binding.yearSpinnerRegister.adapter = adapter
    }

    private fun getNewUserInformation(): UserVO {
        val userName = binding.etNameRegister.text.toString()
        val phoneNumber = intent?.extras?.getString(EXTRA_PHONE_NUMBER,"") ?: ""
        val email = intent?.extras?.getString(EXTRA_EMAIL,"") ?: ""
        val password = binding.etPasswordRegister.text.toString()

        return UserVO(
            userName = userName,
            phoneNumber = phoneNumber,
            email = email,
            password = password,
            birthDate = "$year-$month-$day",
            gender = gender,
            fcmKey = fcmToken,
        )
    }

    private fun setUpGenderRadioGroup() {
        binding.rbMale.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                gender = "Male"
            }
        }

        binding.rbFemale.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                gender = "Female"
            }
        }

        binding.rbOther.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                gender = "Other"
            }
        }
    }

    private fun setUpDateOfBirthSpinners() {

        binding.daySpinnerRegister.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                adapter: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position > 0) {
                    day = adapter?.getItemAtPosition(position).toString()
                    Toast.makeText(this@RegisterActivity,day,Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.monthSpinnerRegister.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                adapter: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                if(position > 0) {
                    month = adapter?.getItemAtPosition(position).toString()
                    Toast.makeText(this@RegisterActivity,month,Toast.LENGTH_SHORT).show()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.yearSpinnerRegister.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                adapter: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                if(position > 0) {
                    year = adapter?.getItemAtPosition(position).toString()
                    Toast.makeText(this@RegisterActivity,year,Toast.LENGTH_SHORT).show()
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    override fun navigateToPreviousScreen() {
        finish()
    }

    override fun navigateToLoginScreen() {

        val dialogBindingRegister = DialogRegisterationSuccessfulBinding.inflate(layoutInflater)
        val dialogRegister = RegisterSuccessfulDialog(this)
        dialogRegister.setContentView(dialogBindingRegister.root)
        dialogRegister.setCancelable(false)

        dialogBindingRegister.btnLoginRegister.setOnClickListener {
            val intent = LoginActivity.newIntent(this)
            startActivity(intent)
            finish()
        }
        dialogRegister.show()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ((requestCode == REQUEST_CODE_GALLERY || requestCode == REQUEST_IMAGE_CAPTURE) && resultCode == Activity.RESULT_OK) {
            val filePath = data?.data

            if(requestCode == REQUEST_IMAGE_CAPTURE) {
                Toast.makeText(this, "You take a photo", Toast.LENGTH_SHORT).show()
                val imageBitmap = data?.extras?.get("data") as Bitmap
                bitmap = imageBitmap
                binding.ivProfileImageRegister.setImageBitmap(bitmap)
                dialog.dismiss()
                return
            }

            Toast.makeText(this, "You choose a photo", Toast.LENGTH_SHORT).show()

            try {
                filePath?.let { fileUrl ->
                    if (Build.VERSION.SDK_INT >= 29) {
                        val source = ImageDecoder.createSource(this.contentResolver, fileUrl)
                        val bitmapImage = ImageDecoder.decodeBitmap(source)
                        bitmap = bitmapImage
                        binding.ivProfileImageRegister.setImageBitmap(bitmap)
                    } else {
                        val bitmapImage = MediaStore.Images.Media.getBitmap(
                            applicationContext.contentResolver, fileUrl
                        )
                        bitmap = bitmapImage
                        binding.ivProfileImageRegister.setImageBitmap(bitmap)
                    }
                }
                dialog.dismiss()
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

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}