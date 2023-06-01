package com.flexath.moments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.flexath.moments.R
import com.flexath.moments.data.vos.VO
import com.flexath.moments.views.viewholders.ActiveChatViewHolder
import com.flexath.moments.views.viewholders.PostViewHolder

class ActiveChatAdapter : BaseRecyclerAdapter<ActiveChatViewHolder, VO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_active_chat_list,parent,false)
        return ActiveChatViewHolder(view)
    }

}
