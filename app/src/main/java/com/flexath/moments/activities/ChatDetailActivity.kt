package com.flexath.moments.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flexath.moments.R
import com.flexath.moments.databinding.ActivityChatDetailBinding

class ChatDetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityChatDetailBinding

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ChatDetailActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    private fun setUpListeners() {

    }
}