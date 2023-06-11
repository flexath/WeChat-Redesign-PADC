package com.flexath.moments.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.flexath.moments.R
import com.flexath.moments.databinding.ActivityRegisterVerificationBinding
import com.flexath.moments.mvp.impls.RegisterVerificationPresenterImpl
import com.flexath.moments.mvp.interfaces.RegisterVerificationPresenter
import com.flexath.moments.mvp.views.RegisterVerificationView

class RegisterVerificationActivity : AppCompatActivity(), RegisterVerificationView {

    private lateinit var binding: ActivityRegisterVerificationBinding

    // Presenters
    private lateinit var mPresenter: RegisterVerificationPresenter

    // General
    private var mOtp:String = ""

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RegisterVerificationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPresenter()

        setUpListeners()

        mPresenter.onUIReady(this)
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[RegisterVerificationPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {
        binding.btnVerifyRegisterVerification.setOnClickListener {
            mPresenter.onTapVerifyButton()
        }

        binding.btnBackRegisterVerification.setOnClickListener {
            mPresenter.onTapBackButton()
        }
    }

    override fun navigateToPreviousScreen() {
        finish()
    }

    override fun navigateToRegisterScreen() {
        val phoneNumber = binding.etPhoneNumberRegisterVerification.text.toString()
        val email = binding.etEmailRegisterVerification.text.toString()

        if(binding.otpPinRegisterVerification.text.toString() == mOtp) {
            startActivity(RegisterActivity.newIntent(this,phoneNumber,email))
        }
    }

    override fun showOtp(otp: String) {
        mOtp = otp
        Toast.makeText(this,otp,Toast.LENGTH_SHORT).show()
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}