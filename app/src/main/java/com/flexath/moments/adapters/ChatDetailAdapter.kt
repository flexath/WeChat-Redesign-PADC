package com.flexath.moments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.R
import com.flexath.moments.data.vos.MessageVO
import com.flexath.moments.views.viewholders.IBaseMessageViewHolder
import com.flexath.moments.views.viewholders.MessageReceiveViewHolder
import com.flexath.moments.views.viewholders.MessageSendViewHolder

class ChatDetailAdapter : RecyclerView.Adapter<IBaseMessageViewHolder>() {

    private val VIEW_TYPE_SEND = 0
    private val VIEW_TYPE_RECEIVE = 1

    private var mMessageList: List<MessageVO> = listOf()
    private var mUserId: String = ""
    private var mCurrentTime = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IBaseMessageViewHolder {
        return if (viewType == VIEW_TYPE_SEND) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_message_send, parent, false)
            MessageSendViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_holder_message_receive, parent, false)
            MessageReceiveViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return mMessageList.size
    }

    override fun onBindViewHolder(holder: IBaseMessageViewHolder, position: Int) {

        when (holder.javaClass) {
            MessageSendViewHolder::class.java -> (holder as MessageSendViewHolder).bindData(
                mMessageList[position]
            )

            MessageReceiveViewHolder::class.java -> (holder as MessageReceiveViewHolder).bindData(
                mMessageList[position]
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = mMessageList[position]
        return if (mUserId == currentMessage.userId) {
            VIEW_TYPE_SEND
        } else {
            VIEW_TYPE_RECEIVE
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(userId: String, messageList: List<MessageVO>) {
        mUserId = userId
        mMessageList = messageList
        notifyDataSetChanged()
    }
}