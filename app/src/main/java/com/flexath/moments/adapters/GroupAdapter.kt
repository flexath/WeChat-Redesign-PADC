package com.flexath.moments.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.R
import com.flexath.moments.data.vos.VO
import com.flexath.moments.databinding.ViewHolderGroupListBinding
import com.flexath.moments.delegates.GroupItemActionDelegate
import com.flexath.moments.views.viewholders.GroupViewHolder

class GroupAdapter(private val delegate:GroupItemActionDelegate) : RecyclerView.Adapter<GroupViewHolder>() {

    private lateinit var binding:ViewHolderGroupListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_group_list,parent,false)
        return GroupViewHolder(view,delegate)
    }

    override fun getItemCount(): Int {
        return 5
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        if(position == 0) {
            binding = ViewHolderGroupListBinding.bind(holder.itemView)
            binding.llGroupItem.setBackgroundResource(R.drawable.background_group_list_accent)
            binding.ivGroupPhoto.setImageResource(R.drawable.dummy_group_example_photo)
            binding.tvGroupName.text = "Add New"
            binding.tvGroupName.setTextColor(Color.WHITE)

            holder.itemView.setOnClickListener {
                delegate.onTapGroupItem()
            }
        }
    }
}