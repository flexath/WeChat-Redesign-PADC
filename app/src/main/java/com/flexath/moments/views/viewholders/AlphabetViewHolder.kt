package com.flexath.moments.views.viewholders

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.databinding.ViewHolderAlphabetListBinding
import com.flexath.moments.delegates.AlphabetActionItemDelegate

class AlphabetViewHolder(itemView: View,private val delegate: AlphabetActionItemDelegate) : RecyclerView.ViewHolder(itemView) {

    private var binding:ViewHolderAlphabetListBinding

    init {
        binding = ViewHolderAlphabetListBinding.bind(itemView)

        itemView.setOnClickListener {
            delegate.onTapAlphabetItem(adapterPosition)
            binding.tvAlphabet.setTextColor(Color.GREEN)
        }
    }

    fun bindData(alphabet: Char) {
        binding.tvAlphabet.text = alphabet.toString()
    }
}