package com.flexath.moments.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.R
import com.flexath.moments.adapters.NewMomentBackgroundAdapter
import com.flexath.moments.databinding.ActivityNewMomentBinding

class NewMomentActivity : AppCompatActivity() {

    private lateinit var binding:ActivityNewMomentBinding
    private lateinit var mAdapter:NewMomentBackgroundAdapter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, NewMomentActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMomentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        mAdapter = NewMomentBackgroundAdapter()
        binding.rvBackground.adapter = mAdapter
        binding.rvBackground.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

        binding.rvBackground.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && binding.etPostNewMoment.isFocused) {
                    binding.etPostNewMoment.clearFocus()
                    hideKeyboard()
                }
            }
        })

    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.etPostNewMoment.windowToken, 0)
    }

}