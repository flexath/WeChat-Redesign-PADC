package com.flexath.moments.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.flexath.moments.databinding.ViewHolderNewMomentBackgroundListBinding
import com.flexath.moments.delegates.NewMomentImageDelegate

class NewMomentBackgroundViewHolder(itemView: View,private val delegate: NewMomentImageDelegate) : RecyclerView.ViewHolder(itemView) {

    private var binding:ViewHolderNewMomentBackgroundListBinding

    init {
        binding = ViewHolderNewMomentBackgroundListBinding.bind(itemView)
    }

    fun bindData(imageUrl: String, position: Int, itemCount: Int) {

        itemView.setOnClickListener {
            if (adapterPosition == itemCount-1) {
                delegate.onTapAddImageButton()
            }
        }

        if(position ==  -1) {
            val image = imageUrl.toInt()
            binding.ivImageNewMoment.setImageResource(image)

        } else {
            Glide.with(itemView.context)
                .load(imageUrl)
                .into(binding.ivImageNewMoment)
        }
    }

}