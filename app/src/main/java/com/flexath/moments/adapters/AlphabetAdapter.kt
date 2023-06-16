package com.flexath.moments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.R
import com.flexath.moments.databinding.ViewHolderAlphabetListBinding
import com.flexath.moments.delegates.AlphabetActionItemDelegate
import com.flexath.moments.views.viewholders.AlphabetViewHolder

class AlphabetAdapter(private val alphabetList: List<Char>,private val delegate:AlphabetActionItemDelegate) : RecyclerView.Adapter<AlphabetViewHolder>() {

    private lateinit var binding: ViewHolderAlphabetListBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlphabetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_alphabet_list,parent,false)
        return AlphabetViewHolder(view,delegate)
    }

    override fun onBindViewHolder(holder: AlphabetViewHolder, position: Int) {
        binding = ViewHolderAlphabetListBinding.bind(holder.itemView)
        if(position == 0) {
            binding.ivStar.visibility = View.VISIBLE
        } else {
            binding.ivStar.visibility = View.GONE
            holder.bindData(alphabetList[position])
        }
    }

    override fun getItemCount(): Int {
        return alphabetList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(alphabetList: List<Char>) {
        notifyDataSetChanged()
    }

}