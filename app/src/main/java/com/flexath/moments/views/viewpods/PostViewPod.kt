package com.flexath.moments.views.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexath.moments.adapters.PostAdapter
import com.flexath.moments.databinding.ViewPodPostBinding

class PostViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    private lateinit var binding:ViewPodPostBinding
    private lateinit var mAdapter:PostAdapter

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = ViewPodPostBinding.bind(this)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        mAdapter = PostAdapter()
        binding.rvPost.adapter = mAdapter
        binding.rvPost.layoutManager = LinearLayoutManager(context)
    }
}