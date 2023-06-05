package com.flexath.moments.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.databinding.ActivityRegisterBinding
import com.flexath.moments.mvp.impls.RegisterPresenterImpl
import com.flexath.moments.mvp.interfaces.RegisterPresenter
import com.flexath.moments.mvp.views.RegisterView
import java.io.IOException
import java.util.Calendar

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity(), RegisterView {

    private lateinit var binding: ActivityRegisterBinding

    // Presenters
    private lateinit var mPresenter: RegisterPresenter

    // General
    private val REQUEST_CODE_GALLERY = 100
    private var day = ""
    private var month = ""
    private var year = ""
    private var gender = ""

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

        binding.btnSignUpRegister.setOnClickListener {
            mPresenter.onTapSignUpButton(getNewUserInformation())
        }

        binding.btnBackRegister.setOnClickListener {
            mPresenter.onTapBackButton()
        }

        binding.ivProfileImageRegister.setOnClickListener {
            mPresenter.onTapProfileImage(getNewUserInformation())
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
            birthDate = "$day-$month-$year",
            gender = gender
        )
    }

    private fun setUpGenderRadioGroup() {

        binding.rbMale.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                gender = "Male"
                Toast.makeText(this,gender,Toast.LENGTH_SHORT).show()
            }
        }

        binding.rbFemale.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                gender = "Female"
                Toast.makeText(this,gender,Toast.LENGTH_SHORT).show()
            }
        }

        binding.rbOther.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                gender = "Other"
                Toast.makeText(this,gender,Toast.LENGTH_SHORT).show()
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
        startActivity(LoginActivity.newIntent(this))
        finish()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }

            val filePath = data.data

            try {
                filePath?.let { fileUrl ->
                    if (Build.VERSION.SDK_INT >= 29) {
                        val source = ImageDecoder.createSource(this.contentResolver, fileUrl)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        mPresenter.onPhotoTaken(bitmap)
                        binding.ivProfileImageRegister.setImageBitmap(bitmap)
                    } else {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            applicationContext.contentResolver, fileUrl
                        )
                        mPresenter.onPhotoTaken(bitmap)
                        binding.ivProfileImageRegister.setImageBitmap(bitmap)
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

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}