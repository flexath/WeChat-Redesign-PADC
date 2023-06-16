package com.flexath.moments.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.R
import com.flexath.moments.data.vos.PrivateMessageVO
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.delegates.ChatItemActionDelegate
import com.flexath.moments.views.viewholders.ChatViewHolder

class ChatAdapter(private val delegate:ChatItemActionDelegate) : RecyclerView.Adapter<ChatViewHolder>() {

    private var mUserList:ArrayList<UserVO> = arrayListOf()
    private var mMessageList:ArrayList<PrivateMessageVO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_chat_list,parent,false)
        return ChatViewHolder(view,delegate)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bindData(mUserList[position], mMessageList[position])
    }

    override fun getItemCount(): Int {
        return mUserList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(userList: ArrayList<UserVO>, messageList: ArrayList<PrivateMessageVO>) {
        mUserList = userList
        mMessageList = messageList
        Log.i("AungThihaFLe",messageList.size.toString())
        notifyDataSetChanged()
    }
}
