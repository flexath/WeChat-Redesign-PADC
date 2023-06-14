package com.flexath.moments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.R
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.delegates.ChatItemActionDelegate
import com.flexath.moments.views.viewholders.ContactsAlphabetGroupViewHolder

class ContactsAlphabetGroupAdapter(private val delegate: ChatItemActionDelegate) : RecyclerView.Adapter<ContactsAlphabetGroupViewHolder>() {

    private var mAlphabetList:List<Char> = listOf()
    private var mUserList:List<UserVO> = listOf()
    private var mIsGroup = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsAlphabetGroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_alphabet_group_contacts_list,parent,false)
        return ContactsAlphabetGroupViewHolder(view,delegate)
    }

    override fun onBindViewHolder(holder: ContactsAlphabetGroupViewHolder, position: Int) {
        holder.bindData(mAlphabetList[position],mUserList,mIsGroup)
    }

    override fun getItemCount(): Int {
        return mAlphabetList.size
    }

    fun getUserCount():Int {
        return mUserList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(alphabetList: List<Char>, userList: List<UserVO>, isGroup: Boolean) {
        mAlphabetList = alphabetList
        mUserList = userList
        mIsGroup = isGroup
        notifyDataSetChanged()
    }


}