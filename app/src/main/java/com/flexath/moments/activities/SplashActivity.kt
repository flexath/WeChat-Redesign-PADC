package com.flexath.moments.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flexath.moments.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpListeners()
    }

    private fun setUpListeners() {
        binding.btnSignUpSplash.setOnClickListener {
            startActivity(RegisterActivity.newIntent(this))
        }

        binding.btnLoginSplash.setOnClickListener {
            startActivity(LoginActivity.newIntent(this))
        }
    }
}