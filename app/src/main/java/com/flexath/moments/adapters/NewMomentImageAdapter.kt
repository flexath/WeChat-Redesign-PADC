package com.flexath.moments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.R
import com.flexath.moments.delegates.NewMomentImageDelegate
import com.flexath.moments.views.viewholders.NewMomentBackgroundViewHolder

class NewMomentImageAdapter(private val delegate: NewMomentImageDelegate) : RecyclerView.Adapter<NewMomentBackgroundViewHolder>() {

     private var imageList = mutableListOf(R.drawable.baseline_add_accent_24dp.toString())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewMomentBackgroundViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_new_moment_background_list,parent,false)
        return NewMomentBackgroundViewHolder(view,delegate)
    }

    override fun onBindViewHolder(holder: NewMomentBackgroundViewHolder, position: Int) {
        if(position == imageList.lastIndex) {
            holder.bindData(imageList[position],-1,itemCount)
        }else {
            holder.bindData(imageList[position],position,itemCount)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(imageString:String) {
        imageList.add(0,imageString)
//        imageList = imageString.splitToSequence(',').toMutableList()
//        imageList.removeLast()
//        imageList.add(0,imageString)
        notifyDataSetChanged()
    }
}
