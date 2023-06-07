package com.flexath.moments.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flexath.moments.databinding.ViewHolderMomentImagesListBinding

class MomentImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding:ViewHolderMomentImagesListBinding

    init {
        binding = ViewHolderMomentImagesListBinding.bind(itemView)
    }

    fun bindNewData(image: String) {
        Glide.with(itemView.context)
            .load(image)
            .into(binding.ivMomentPictureMoment)
    }
}