package com.flexath.moments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.R
import com.flexath.moments.data.vos.MomentVO
import com.flexath.moments.delegates.MomentItemActionDelegate
import com.flexath.moments.views.viewholders.MomentViewHolder

class MomentAdapter(private val delegate:MomentItemActionDelegate) : RecyclerView.Adapter<MomentViewHolder>() {

    private var mMomentList:List<MomentVO> = listOf()
    private var mTabName:String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MomentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_moment_list,parent,false)
        return MomentViewHolder(view,delegate)
    }

    override fun onBindViewHolder(holder: MomentViewHolder, position: Int) {
        holder.bindData(mMomentList[position],mTabName)
    }

    override fun getItemCount(): Int {
        return mMomentList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(momentList: List<MomentVO>, tabName: String) {
        mMomentList = momentList.sortedByDescending {
            it.id
        }
        mTabName = tabName
        notifyDataSetChanged()
    }


}
