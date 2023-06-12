package com.flexath.moments.views.viewholders

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.R
import com.flexath.moments.data.vos.GroupVO
import com.flexath.moments.databinding.ViewHolderGroupListBinding
import com.flexath.moments.delegates.GroupItemActionDelegate

class GroupViewHolder(itemView: View, private val delegate: GroupItemActionDelegate) :
    RecyclerView.ViewHolder(itemView) {

    private var binding: ViewHolderGroupListBinding

    init {
        binding = ViewHolderGroupListBinding.bind(itemView)
    }

    fun bindData(group: GroupVO) {
//        if (adapterPosition == 0) {
//            binding.llGroupItem.setBackgroundResource(R.drawable.background_group_list_accent)
//            binding.ivGroupPhoto.setImageResource(R.drawable.dummy_group_example_photo)
//            binding.tvGroupName.text = "Add New"
//            binding.tvGroupName.setTextColor(Color.WHITE)
//
//            itemView.setOnClickListener {
//                delegate.onTapGroupItem()
//            }
//        } else {
//            binding.tvGroupName.text = group.name
//        }
        binding.tvGroupName.text = group.name

        itemView.setOnClickListener {
            delegate.onTapGroupItem(group.id)
        }
    }

}