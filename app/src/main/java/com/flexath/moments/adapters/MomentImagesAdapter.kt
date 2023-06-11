package com.flexath.moments.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.R
import com.flexath.moments.views.viewholders.MomentImagesViewHolder

class MomentImagesAdapter : RecyclerView.Adapter<MomentImagesViewHolder>() {

     private var mImageList:List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentImagesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_moment_images_list,parent,false)
        return MomentImagesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MomentImagesViewHolder, position: Int) {
        holder.bindNewData(mImageList[position],itemCount)
    }

    override fun getItemCount(): Int {
        return mImageList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(imageList:List<String>) {
        mImageList = imageList
        Log.i("ImageListAth",mImageList.toString())
        notifyDataSetChanged()
    }
}