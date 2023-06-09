package com.flexath.moments.views.viewholders

import android.view.View
import com.flexath.moments.data.vos.MessageVO
import com.flexath.moments.databinding.ViewHolderMessageSendBinding

class MessageSendViewHolder(itemView: View) : IBaseMessageViewHolder(itemView) {

    private var binding:ViewHolderMessageSendBinding

    init {
        binding = ViewHolderMessageSendBinding.bind(itemView)
    }

    override fun bindData(message: MessageVO) {
        binding.tvSentMessage.text = message.message
    }
}