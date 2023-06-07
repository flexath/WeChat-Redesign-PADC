package com.flexath.moments.views.viewholders

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.flexath.moments.R
import com.flexath.moments.adapters.MomentImagesAdapter
import com.flexath.moments.data.vos.MomentVO
import com.flexath.moments.databinding.ViewHolderMomentListBinding
import com.flexath.moments.delegates.MomentItemActionDelegate
import io.reactivex.internal.util.LinkedArrayList

class MomentViewHolder(itemView: View,private val delegate: MomentItemActionDelegate) : BaseViewHolder<MomentVO>(itemView) {

    private var binding:ViewHolderMomentListBinding

    // Adapters
    private lateinit var mAdapter: MomentImagesAdapter

    // General
    private var mMoment:MomentVO? = null

    init {
        binding = ViewHolderMomentListBinding.bind(itemView)
        setUpListeners()
    }

    private fun setUpListeners() {
        binding.btnMomentOption.setOnClickListener {
            delegate.onTapOptionButton()
        }

        binding.btnMomentBookmark.setOnClickListener {
            if(mMoment?.isBookmarked == true) {
                delegate.onTapBookmarkButton(mMoment?.id ?: "",false)
            } else {
                delegate.onTapBookmarkButton(mMoment?.id ?: "",true)
            }
        }
    }

    override fun bindData(data: MomentVO) {
        mMoment = data

        binding.tvMomentProfileName.text = data.userName
        binding.tvMomentCaption.text = data.caption

        Glide.with(itemView.context)
            .load(data.userProfileImage)
            .into(binding.ivMomentProfilePic)

        setUpMomentImages()
        mAdapter.setNewData(changeImageStringToList(data.imageUrl))

        if(data.isBookmarked) {
            binding.btnMomentBookmark.setImageResource(R.drawable.baseline_bookmark_red_24dp)
        } else {
            binding.btnMomentBookmark.setImageResource(R.drawable.baseline_bookmark_border_accent_24dp)
        }
    }

    private fun changeImageStringToList(imageString:String) : List<String> {
        return imageString.split(',').toList()
    }

    private fun setUpMomentImages() {
        mAdapter = MomentImagesAdapter()
        binding.viewPagerMomentImages.adapter = mAdapter
    }

}