package com.flexath.moments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.R
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.views.viewholders.ActiveChatViewHolder

class ActiveChatAdapter : RecyclerView.Adapter<ActiveChatViewHolder>() {

    private var mContactList:List<UserVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_active_chat_list,parent,false)
        return ActiveChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ActiveChatViewHolder, position: Int) {
        holder.bindData(mContactList[position])
    }

    override fun getItemCount(): Int {
        return mContactList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(contactList: List<UserVO>) {
        mContactList = contactList
        notifyDataSetChanged()
    }

}
