package com.flexath.moments.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.flexath.moments.databinding.ActivityLoginBinding
import com.flexath.moments.mvp.impls.LoginPresenterImpl
import com.flexath.moments.mvp.interfaces.BasePresenter
import com.flexath.moments.mvp.interfaces.LoginPresenter
import com.flexath.moments.mvp.views.LoginView

class LoginActivity : AppCompatActivity() , LoginView {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var mPresenter:LoginPresenter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context,LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPresenter()

        setUpListeners()
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[LoginPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {
        binding.btnLogin.setOnClickListener {
            mPresenter.onTapLoginButton(
                binding.etPhoneNumberLogin.text.toString(),
                binding.etEmailLogin.text.toString(),
                binding.etPasswordLogin.text.toString()
            )
        }

        binding.btnBackLogin.setOnClickListener {
            mPresenter.onTapBackButton()
        }
    }

    override fun navigateToPreviousScreen() {
        finish()
    }

    override fun navigateToHomeScreen() {
        startActivity(MainActivity.newIntent(this))
    }

    override fun showError(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
    }
}