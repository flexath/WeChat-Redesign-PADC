package com.flexath.moments.views.viewholders

import android.view.View
import com.flexath.moments.databinding.ViewHolderNewMomentBackgroundListBinding

class NewMomentBackgroundViewHolder(itemView: View) : BaseViewHolder<String>(itemView) {

    private var binding:ViewHolderNewMomentBackgroundListBinding

    init {
        binding = ViewHolderNewMomentBackgroundListBinding.bind(itemView)
    }

    override fun bindData(data: String) {

    }

}