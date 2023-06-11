package com.flexath.moments.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.data.vos.VO
import com.flexath.moments.databinding.ViewHolderChatListBinding
import com.flexath.moments.delegates.ChatItemActionDelegate

class ChatViewHolder(itemView: View,private val delegate: ChatItemActionDelegate) :
    RecyclerView.ViewHolder(itemView) {

    private var binding:ViewHolderChatListBinding
    private var mUserId:String? = null

    init {
        binding = ViewHolderChatListBinding.bind(itemView)

        setUpListeners()
    }

    private fun setUpListeners() {
        itemView.setOnClickListener {
            mUserId?.let { userId ->
                delegate.onTapChatItem(userId)
            }
        }
    }

    fun bindData(user: UserVO) {
        mUserId = user.userId

        binding.tvNameChat.text = user.userName

        Glide.with(itemView.context)
            .load(user.imageUrl)
            .into(binding.ivProfileImageChat)
    }


}