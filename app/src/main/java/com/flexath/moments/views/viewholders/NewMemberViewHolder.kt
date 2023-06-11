package com.flexath.moments.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.databinding.ViewHolderNewMemberListBinding

class NewMemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding:ViewHolderNewMemberListBinding

    init {
        binding = ViewHolderNewMemberListBinding.bind(itemView)
        bringRemoveButtonToForeground()
    }

    private fun bringRemoveButtonToForeground() {
        binding.ivRemoveMember.bringToFront()
        binding.ivRemoveMember.translationZ = 8f
    }

    fun bindData(user: UserVO) {
        Glide.with(itemView.context)
            .load(user.imageUrl)
            .into(binding.ivProfileImageNewMemberGroup)
    }
}