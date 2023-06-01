package com.flexath.moments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.flexath.moments.R
import com.flexath.moments.data.vos.VO
import com.flexath.moments.delegates.ChatItemActionDelegate
import com.flexath.moments.views.viewholders.ChatViewHolder

class ChatAdapter(private val delegate:ChatItemActionDelegate) : BaseRecyclerAdapter<ChatViewHolder, VO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_chat_list,parent,false)
        return ChatViewHolder(view,delegate)
    }
}
