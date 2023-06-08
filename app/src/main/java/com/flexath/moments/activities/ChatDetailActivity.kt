package com.flexath.moments.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.flexath.moments.databinding.ActivityChatDetailBinding

class ChatDetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityChatDetailBinding

    companion object {
        private const val EXTRA_USER_ID = "UserId"
        fun newIntent(context: Context,userId:String): Intent {
            val intent = Intent(context, ChatDetailActivity::class.java)
            intent.putExtra(EXTRA_USER_ID,userId)
            return intent
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