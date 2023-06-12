package com.flexath.moments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.R
import com.flexath.moments.views.viewholders.ImageChatDetailViewHolder

class ImageChatDetailAdapter : RecyclerView.Adapter<ImageChatDetailViewHolder>() {

    private var mImageList:List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageChatDetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_chat_detail_image_list,parent,false)
        return ImageChatDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageChatDetailViewHolder, position: Int) {
        holder.bindNewData(mImageList[position])
    }

    override fun getItemCount(): Int {
        return mImageList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(imageList:List<String>) {
        mImageList = imageList
        notifyDataSetChanged()
    }
}