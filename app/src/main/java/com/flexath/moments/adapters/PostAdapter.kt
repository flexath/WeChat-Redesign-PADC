package com.flexath.moments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.flexath.moments.R
import com.flexath.moments.data.vos.VO
import com.flexath.moments.views.viewholders.PostViewHolder

class PostAdapter : BaseRecyclerAdapter<PostViewHolder, VO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_post_list,parent,false)
        return PostViewHolder(view)
    }

}
