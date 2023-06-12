package com.flexath.moments.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flexath.moments.databinding.ViewHolderChatDetailImageListBinding

class ImageChatDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding:ViewHolderChatDetailImageListBinding

    init {
        binding = ViewHolderChatDetailImageListBinding.bind(itemView)
    }

    fun bindNewData(imageUrl:String) {
        Glide.with(itemView.context)
            .load(imageUrl)
            .into(binding.ivSendImage)
    }
}