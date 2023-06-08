package com.flexath.moments.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.databinding.ViewHolderContactsListBinding

class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding:ViewHolderContactsListBinding

    init {
        binding = ViewHolderContactsListBinding.bind(itemView)
    }

    fun bindData(user: UserVO) {
        binding.tvNameContacts.text = user.userName

        Glide.with(itemView.context)
            .load(user.imageUrl)
            .into(binding.ivProfileImageContacts)
    }
}