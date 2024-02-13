package com.sbapps.scheduleplus.presentation.adapters.scheduleitem

import androidx.recyclerview.widget.DiffUtil
import com.sbapps.scheduleplus.domain.entity.ScheduleItem

class ScheduleItemListDiffCallback : DiffUtil.ItemCallback<ScheduleItem>() {
    override fun areItemsTheSame(oldItem: ScheduleItem, newItem: ScheduleItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ScheduleItem, newItem: ScheduleItem): Boolean {
        return oldItem == newItem
    }
}