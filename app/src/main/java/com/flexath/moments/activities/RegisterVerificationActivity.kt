package com.flexath.moments.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flexath.moments.R
import com.flexath.moments.databinding.ActivityRegisterVerificationBinding

class RegisterVerificationActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRegisterVerificationBinding

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, RegisterVerificationActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpListeners()
    }

    private fun setUpListeners() {
        binding.btnVerifyRegisterVerification.setOnClickListener {
            startActivity(LoginActivity.newIntent(this))
            finish()
        }
    }
}