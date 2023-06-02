package com.flexath.moments.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.flexath.moments.R
import com.flexath.moments.databinding.ActivityRegisterBinding
import com.flexath.moments.mvp.impls.RegisterPresenterImpl
import com.flexath.moments.mvp.interfaces.RegisterPresenter
import com.flexath.moments.mvp.views.RegisterView
import java.util.Calendar


class RegisterActivity : AppCompatActivity(),RegisterView {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var mPresenter:RegisterPresenter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RegisterActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPresenter()

        setUpListeners()
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[RegisterPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {
        setUpYearSpinner()

        binding.btnSignUpRegister.setOnClickListener {
            mPresenter.onTapSignUpButton()
        }

        binding.btnBackRegister.setOnClickListener {
            mPresenter.onTapBackButton()
        }
    }

    private fun setUpYearSpinner() {
        val years = arrayListOf("Year")
        val thisYear: Int = Calendar.getInstance().get(Calendar.YEAR)
        for (year in 1900..thisYear) {
            years.add(year.toString())
        }

        val adapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, years)
        binding.yearSpinnerRegister.adapter = adapter
    }

    override fun navigateToPreviousScreen() {
        finish()
    }

    override fun navigateToRegisterVerificationScreen() {
        startActivity(RegisterVerificationActivity.newIntent(this))
    }

    override fun showError(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
    }
}