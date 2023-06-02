package com.flexath.moments.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flexath.moments.data.ImageItem
import com.flexath.moments.databinding.ViewHolderGalleryImagesListBinding

class GalleryImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding: ViewHolderGalleryImagesListBinding

    init {
        binding = ViewHolderGalleryImagesListBinding.bind(itemView)
    }


    fun bind(imageItem: ImageItem) {
        Glide.with(itemView.context)
            .load(imageItem.imagePath)
            .into(binding.ivGallery)
    }

}