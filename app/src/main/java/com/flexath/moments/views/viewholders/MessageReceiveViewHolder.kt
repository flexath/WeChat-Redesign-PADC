package com.flexath.moments.views.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.flexath.moments.data.vos.MessageVO
import com.flexath.moments.databinding.ViewHolderMessageReceiveBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MessageReceiveViewHolder(itemView: View) : IBaseMessageViewHolder(itemView) {

    private var binding:ViewHolderMessageReceiveBinding

    init {
        binding = ViewHolderMessageReceiveBinding.bind(itemView)
    }

    override fun bindData(message: MessageVO) {
        binding.tvReceivedMessage.text = message.message

        binding.tvTimeReceiveMessage.text = getCurrentHourAndMinutes(message.timeStamp)

        Glide.with(itemView.context)
            .load(message.userProfileImage)
            .into(binding.ivProfileChatHead)

        if(message.file.isNotEmpty() && message.message.isEmpty()) {
            binding.rlChatMessage.visibility = View.GONE

            binding.ivReceiveImage.visibility = View.VISIBLE
            binding.mcvReceiveImageChatDetail.visibility = View.VISIBLE

            Glide.with(itemView.context)
                .load(message.file)
                .into(binding.ivReceiveImage)
        }
    }

    private fun getCurrentHourAndMinutes(currentTimeMillis:Long) :String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = currentTimeMillis

        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)

        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return timeFormat.format(calendar.time)
    }
}