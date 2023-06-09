package com.flexath.moments.views.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.flexath.moments.data.vos.MessageVO
import com.flexath.moments.databinding.ViewHolderMessageReceiveBinding

class MessageReceiveViewHolder(itemView: View) : IBaseMessageViewHolder(itemView) {

    private var binding:ViewHolderMessageReceiveBinding

    init {
        binding = ViewHolderMessageReceiveBinding.bind(itemView)
    }

    override fun bindData(message: MessageVO) {
        binding.tvReceivedMessage.text = message.message

        Glide.with(itemView.context)
            .load(message.userProfileImage)
            .into(binding.ivProfileChatHead)
    }
}