package com.flexath.moments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.flexath.moments.R
import com.flexath.moments.views.viewholders.NewMomentBackgroundViewHolder

class NewMomentBackgroundAdapter : BaseRecyclerAdapter<NewMomentBackgroundViewHolder, String>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewMomentBackgroundViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_new_moment_background_list,parent,false)
        return NewMomentBackgroundViewHolder(view)
    }

}
