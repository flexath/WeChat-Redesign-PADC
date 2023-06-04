package com.flexath.moments.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.databinding.ViewHolderContactsListBinding

class ContactsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding:ViewHolderContactsListBinding

    init {
        binding = ViewHolderContactsListBinding.bind(itemView)
    }

    fun bindData(name: String) {
        binding.tvNameContacts.text = name
    }
}