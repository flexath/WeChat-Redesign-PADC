package com.flexath.moments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.R
import com.flexath.moments.data.vos.GroupVO
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.delegates.ChatItemActionDelegate
import com.flexath.moments.delegates.GroupItemActionDelegate
import com.flexath.moments.views.viewholders.ChatViewHolder
import com.flexath.moments.views.viewholders.GroupChatViewHolder

class GroupChatAdapter(private val delegate:GroupItemActionDelegate) : RecyclerView.Adapter<GroupChatViewHolder>() {

    private var mGroupList:ArrayList<GroupVO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_chat_list,parent,false)
        return GroupChatViewHolder(view,delegate)
    }

    override fun onBindViewHolder(holder: GroupChatViewHolder, position: Int) {
        holder.bindData(mGroupList[position])
    }

    override fun getItemCount(): Int {
        return mGroupList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(group: GroupVO) {
        mGroupList.add(group)
        notifyDataSetChanged()
    }
}
