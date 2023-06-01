package com.flexath.moments.views.viewholders

import android.view.View
import com.flexath.moments.data.vos.VO
import com.flexath.moments.databinding.ViewHolderChatListBinding
import com.flexath.moments.delegates.ChatItemActionDelegate

class ChatViewHolder(itemView: View,private val delegate: ChatItemActionDelegate) : BaseViewHolder<VO>(itemView) {

    private var binding:ViewHolderChatListBinding

    init {
        binding = ViewHolderChatListBinding.bind(itemView)

        setUpListeners()
    }

    private fun setUpListeners() {
        itemView.setOnClickListener {
            delegate.onTapChatItem()
        }
    }

    override fun bindData(data: VO) {

    }

}