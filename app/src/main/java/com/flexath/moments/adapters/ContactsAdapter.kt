package com.flexath.moments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.R
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.views.viewholders.ContactsViewHolder

class ContactsAdapter : RecyclerView.Adapter<ContactsViewHolder>() {

     private var mNameList:ArrayList<UserVO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_contacts_list,parent,false)
        return ContactsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bindData(mNameList[position])
    }

    override fun getItemCount(): Int {
        return mNameList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(nameList: ArrayList<UserVO>) {
        mNameList = nameList
        notifyDataSetChanged()
    }
}