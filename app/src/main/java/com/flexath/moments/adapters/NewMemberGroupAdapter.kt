package com.flexath.moments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.R
import com.flexath.moments.views.viewholders.NewMemberViewHolder

class NewMemberGroupAdapter : RecyclerView.Adapter<NewMemberViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewMemberViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_new_member_list,parent,false)
        return NewMemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewMemberViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 3
    }
}