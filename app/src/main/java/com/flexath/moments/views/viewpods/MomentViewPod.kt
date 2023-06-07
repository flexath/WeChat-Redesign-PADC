package com.flexath.moments.views.viewpods

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.flexath.moments.adapters.MomentAdapter
import com.flexath.moments.data.vos.MomentVO
import com.flexath.moments.databinding.ViewPodPostBinding
import com.flexath.moments.delegates.MomentItemActionDelegate

class MomentViewPod @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    private lateinit var binding:ViewPodPostBinding
    private lateinit var mAdapter:MomentAdapter

    private lateinit var mDelegate:MomentItemActionDelegate

    override fun onFinishInflate() {
        super.onFinishInflate()
        binding = ViewPodPostBinding.bind(this)

    }

    fun setDelegate(delegate:MomentItemActionDelegate) {
        this.mDelegate = delegate
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        mAdapter = MomentAdapter(mDelegate)
        binding.rvMoment.adapter = mAdapter
        binding.rvMoment.layoutManager = LinearLayoutManager(context)
    }

    fun setNewData(momentList: List<MomentVO>) {
        mAdapter.setNewData(momentList)
    }
}