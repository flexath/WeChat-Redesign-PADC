package com.flexath.moments.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flexath.moments.R
import com.flexath.moments.data.vos.GroupVO
import com.flexath.moments.data.vos.PrivateMessageVO
import com.flexath.moments.databinding.ViewHolderChatListBinding
import com.flexath.moments.delegates.GroupItemActionDelegate

class GroupChatViewHolder(itemView: View, private val delegate: GroupItemActionDelegate) :
    RecyclerView.ViewHolder(itemView) {

    private var binding:ViewHolderChatListBinding
    private var mGroupId:Long? = null

    init {
        binding = ViewHolderChatListBinding.bind(itemView)
        setUpListeners()
    }

    private fun setUpListeners() {
        itemView.setOnClickListener {
            mGroupId?.let { id ->
                delegate.onTapGroupItem(id)
            }
        }
    }

    fun bindData(group: GroupVO) {
        mGroupId = group.id

        binding.tvNameChat.text = group.name

        binding.tvLastMessage.text = "You sent a message"

        Glide.with(itemView.context)
            .load(group.imageUrl)
            .placeholder(R.drawable.dummy_group_photo)
            .into(binding.ivProfileImageChat)


    }


}