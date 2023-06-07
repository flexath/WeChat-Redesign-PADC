package com.flexath.moments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.flexath.moments.R
import com.flexath.moments.data.vos.MomentVO
import com.flexath.moments.delegates.MomentItemActionDelegate
import com.flexath.moments.views.viewholders.MomentViewHolder

class MomentAdapter(private val delegate:MomentItemActionDelegate) : BaseRecyclerAdapter<MomentViewHolder, MomentVO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_moment_list,parent,false)
        return MomentViewHolder(view,delegate)
    }

}
