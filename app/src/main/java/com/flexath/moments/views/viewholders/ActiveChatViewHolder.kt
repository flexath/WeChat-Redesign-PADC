package com.flexath.moments.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.data.vos.VO
import com.flexath.moments.databinding.ViewHolderActiveChatListBinding

class ActiveChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding:ViewHolderActiveChatListBinding

    init {
        binding = ViewHolderActiveChatListBinding.bind(itemView)
    }

    fun bindData(user: UserVO) {
        Glide.with(itemView.context)
            .load(user.imageUrl)
            .into(binding.ivProfileImageActiveChat)
    }

}