package com.flexath.moments.views.viewholders

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.adapters.ContactsAdapter
import com.flexath.moments.data.vos.UserVO
import com.flexath.moments.databinding.ViewHolderAlphabetGroupContactsListBinding

class ContactsAlphabetGroupViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding:ViewHolderAlphabetGroupContactsListBinding

    // Adapters
    private lateinit var mContactsAdapter:ContactsAdapter

    init {
        binding = ViewHolderAlphabetGroupContactsListBinding.bind(itemView)
        setUpContactsAlphabetGroupAdapter()
    }

    private fun setUpContactsAlphabetGroupAdapter() {
        mContactsAdapter = ContactsAdapter()
        binding.rvContacts.adapter = mContactsAdapter
        binding.rvContacts.layoutManager = LinearLayoutManager(itemView.context)
    }

    fun bindData(firstAlphabet: Char, contactList: List<UserVO>) {
        binding.tvAlphabetContacts.text =  firstAlphabet.toString()

        val userList = arrayListOf<UserVO>()

        for(user in contactList) {
            if(firstAlphabet == user.userName[0]) {
                userList.add(user)
            }
        }

        mContactsAdapter.setNewData(userList)
    }
}