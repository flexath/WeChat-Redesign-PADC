package com.flexath.moments.views.viewholders

import android.view.View
import com.flexath.moments.data.vos.VO
import com.flexath.moments.databinding.ViewHolderActiveChatListBinding

class ActiveChatViewHolder(itemView: View) : BaseViewHolder<VO>(itemView) {

    private var binding:ViewHolderActiveChatListBinding

    init {
        binding = ViewHolderActiveChatListBinding.bind(itemView)
    }

    override fun bindData(data: VO) {

    }

}