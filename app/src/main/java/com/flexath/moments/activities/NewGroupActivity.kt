package com.flexath.moments.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexath.moments.R
import com.flexath.moments.adapters.NewMemberGroupAdapter
import com.flexath.moments.databinding.ActivityNewGroupBinding

class NewGroupActivity : AppCompatActivity() {

    private lateinit var binding:ActivityNewGroupBinding
    private lateinit var mAdapter:NewMemberGroupAdapter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, NewGroupActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewGroupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        mAdapter = NewMemberGroupAdapter()
        binding.rvMemberNewGroup.adapter = mAdapter
        binding.rvMemberNewGroup.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
    }
}