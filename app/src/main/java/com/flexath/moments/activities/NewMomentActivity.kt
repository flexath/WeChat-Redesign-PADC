package com.flexath.moments.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.R
import com.flexath.moments.adapters.NewMomentBackgroundAdapter
import com.flexath.moments.databinding.ActivityNewMomentBinding
import com.flexath.moments.mvp.impls.NewMomentPresenterImpl
import com.flexath.moments.mvp.interfaces.NewMomentPresenter
import com.flexath.moments.mvp.views.NewMomentView

class NewMomentActivity : AppCompatActivity() , NewMomentView {

    private lateinit var binding:ActivityNewMomentBinding

    // Adapter
    private lateinit var mAdapter:NewMomentBackgroundAdapter

    // Presenter
    private lateinit var mPresenter:NewMomentPresenter

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, NewMomentActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewMomentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpPresenter()

        setUpListeners()
        setUpRecyclerView()
    }

    private fun setUpPresenter() {
        mPresenter = ViewModelProvider(this)[NewMomentPresenterImpl::class.java]
        mPresenter.initPresenter(this)
    }

    private fun setUpListeners() {
        binding.btnBackNewMoment.setOnClickListener {
            mPresenter.onTapBackButton()
        }

        binding.btnCreateNewMoment.setOnClickListener {
            mPresenter.onTapCreateButton()
        }
    }

    private fun setUpRecyclerView() {
        mAdapter = NewMomentBackgroundAdapter()
        binding.rvBackground.adapter = mAdapter
        binding.rvBackground.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

//        binding.rvBackground.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                if (dy > 0 && binding.etPostNewMoment.isFocused) {
//                    binding.etPostNewMoment.clearFocus()
//                    hideKeyboard()
//                }
//            }
//        })

    }
    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.etPostNewMoment.windowToken, 0)
    }

    override fun createNewMoment() {
        finish()
    }

    override fun navigateToPreviousScreen() {
        finish()
    }

    override fun showError(error: String) {
        Toast.makeText(this,error,Toast.LENGTH_SHORT).show()
    }

}