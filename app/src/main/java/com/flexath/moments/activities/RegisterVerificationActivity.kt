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
        startActivity(RegisterActivity.newIntent(this,phoneNumber,email))
        finish()
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}