package com.flexath.moments.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.flexath.moments.data.vos.PrivateMessageVO

abstract class IBaseMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bindData(message: PrivateMessageVO)
}