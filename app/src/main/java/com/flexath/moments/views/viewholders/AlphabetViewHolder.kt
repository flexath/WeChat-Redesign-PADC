package com.flexath.moments.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.databinding.ViewHolderAlphabetListBinding

class AlphabetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var binding:ViewHolderAlphabetListBinding

    init {
        binding = ViewHolderAlphabetListBinding.bind(itemView)
    }

    fun bindData(alphabet: Char) {
        binding.tvAlphabet.text = alphabet.toString()
    }
}