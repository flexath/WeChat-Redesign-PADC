package com.flexath.moments.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.views.viewholders.BaseViewHolder

abstract class BaseRecyclerAdapter<T : BaseViewHolder<W>, W> : RecyclerView.Adapter<T>() {

    private var mData: ArrayList<W> = arrayListOf()

    override fun onBindViewHolder(holder: T, position: Int) {
        if (mData.size > 0) {
            holder.bindData(mData[position])
        }
    }

    override fun getItemCount(): Int {
        return 7
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(newData: List<W>) {
        if (newData.isEmpty()) {
            mData.clear()
        } else {
            mData = ArrayList(newData)
        }
        notifyDataSetChanged()
    }
}