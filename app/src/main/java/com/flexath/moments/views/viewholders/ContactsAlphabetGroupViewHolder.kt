package com.flexath.moments.views.viewholders

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.adapters.ContactsAdapter
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

    fun bindData(firstAlphabet: Char, nameList: List<String>) {
        binding.tvAlphabetContacts.text =  firstAlphabet.toString()

        val names = arrayListOf<String>()
        for(name in nameList) {
            if(firstAlphabet == name[0]) {
                names.add(name)
            }
        }

        mContactsAdapter.setNewData(names)
    }
}