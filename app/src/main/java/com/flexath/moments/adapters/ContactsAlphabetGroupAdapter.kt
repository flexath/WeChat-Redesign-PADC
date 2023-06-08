package com.flexath.moments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.R
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.views.viewholders.ContactsAlphabetGroupViewHolder

class ContactsAlphabetGroupAdapter : RecyclerView.Adapter<ContactsAlphabetGroupViewHolder>() {

    private var mAlphabetList:List<Char> = listOf()
    private var mUserList:List<UserVO> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAlphabetGroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_alphabet_group_contacts_list,parent,false)
        return ContactsAlphabetGroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactsAlphabetGroupViewHolder, position: Int) {
        holder.bindData(mAlphabetList[position],mUserList)
    }

    override fun getItemCount(): Int {
        return mAlphabetList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(alphabetList: List<Char>, userList: List<UserVO>) {
        mAlphabetList = alphabetList
        mUserList = userList
        notifyDataSetChanged()
    }


}