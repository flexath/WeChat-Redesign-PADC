package com.flexath.moments.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.flexath.moments.R
import com.flexath.moments.databinding.ActivityMainBinding
import com.flexath.moments.fragments.ChatFragment
import com.flexath.moments.fragments.ContactsFragment
import com.flexath.moments.fragments.MomentFragment
import com.flexath.moments.fragments.ProfileFragment
import com.flexath.moments.fragments.SettingFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpBottomNavigationView()
    }

    private fun setUpBottomNavigationView() {
        switchFragment(MomentFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.nvgMoment -> {
                    switchFragment(MomentFragment())
                    true
                }
                R.id.nvgChat -> {
                    switchFragment(ChatFragment())
                    true
                }
                R.id.nvgContacts -> {
                    switchFragment(ContactsFragment())
                    true
                }
                R.id.nvgMe -> {
                    switchFragment(ProfileFragment())
                    true
                }
                R.id.nvgSetting -> {
                    switchFragment(SettingFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,fragment)
            .commit()
    }
}