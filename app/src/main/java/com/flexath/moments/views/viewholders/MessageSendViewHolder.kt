package com.flexath.moments.views.viewholders

import android.view.View
import com.bumptech.glide.Glide
import com.flexath.moments.data.vos.MessageVO
import com.flexath.moments.databinding.ViewHolderMessageSendBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MessageSendViewHolder(itemView: View) : IBaseMessageViewHolder(itemView) {

    private var binding: ViewHolderMessageSendBinding

    init {
        binding = ViewHolderMessageSendBinding.bind(itemView)
    }

    override fun bindData(message: MessageVO) {

        if (message.message.isEmpty() && message.file.isNotEmpty()) {
            binding.flSendMessage.visibility = View.GONE
            binding.mcvSendImageChatDetail.visibility = View.VISIBLE

            Glide.with(itemView.context)
                .load(message.file)
                .into(binding.ivSendImageChatDetail)

        } else if (message.message.isNotEmpty() && message.file.isEmpty()) {
            binding.flSendMessage.visibility = View.VISIBLE
            binding.mcvSendImageChatDetail.visibility = View.GONE

            binding.tvSentMessage.text = message.message
            binding.tvTimeSendMessage.text = getCurrentHourAndMinutes(message.timeStamp)
        } else {
            binding.flSendMessage.visibility = View.VISIBLE
            binding.mcvSendImageChatDetail.visibility = View.VISIBLE

            binding.tvSentMessage.text = message.message
            binding.tvTimeSendMessage.text = getCurrentHourAndMinutes(message.timeStamp)

            Glide.with(itemView.context)
                .load(message.file)
                .into(binding.ivSendImageChatDetail)
        }
    }

    private fun getCurrentHourAndMinutes(currentTimeMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = currentTimeMillis

        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)

        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return timeFormat.format(calendar.time)
    }
}