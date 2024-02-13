package com.sbapps.scheduleplus.presentation.adapters.week

import androidx.recyclerview.widget.DiffUtil
import com.sbapps.scheduleplus.domain.entity.Week

class WeekItemDiffCallback : DiffUtil.ItemCallback<Week>() {
    override fun areItemsTheSame(oldItem: Week, newItem: Week): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Week, newItem: Week): Boolean {
        return oldItem == newItem
    }
}