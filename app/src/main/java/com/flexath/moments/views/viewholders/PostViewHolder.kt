package com.flexath.moments.views.viewholders

import android.view.View
import com.flexath.moments.data.vos.VO
import com.flexath.moments.databinding.ViewHolderPostListBinding

class PostViewHolder(itemView: View) : BaseViewHolder<VO>(itemView) {

    private var binding:ViewHolderPostListBinding

    init {
        binding = ViewHolderPostListBinding.bind(itemView)
    }

    override fun bindData(data: VO) {

    }

}