package com.flexath.moments.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.databinding.ViewHolderContactsListBinding
import com.flexath.moments.delegates.ChatItemActionDelegate

class ContactsViewHolder(itemView: View,private val delegate: ChatItemActionDelegate) : RecyclerView.ViewHolder(itemView) {

    private var binding:ViewHolderContactsListBinding
    private var mUser:UserVO? = null

    init {
        binding = ViewHolderContactsListBinding.bind(itemView)

        itemView.setOnClickListener {
            delegate.onTapChatItem(mUser?.userId ?: "")
        }
    }

    fun bindData(user: UserVO, isGroup: Boolean) {
        mUser = user
        binding.tvNameContacts.text = user.userName

        Glide.with(itemView.context)
            .load(user.imageUrl)
            .into(binding.ivProfileImageContacts)

        if(isGroup) {
            binding.cbGroup.visibility = View.VISIBLE
        } else {
            binding.cbGroup.visibility = View.GONE
        }
    }
}