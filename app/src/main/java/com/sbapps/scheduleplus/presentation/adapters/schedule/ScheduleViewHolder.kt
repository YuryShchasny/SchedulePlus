package com.sbapps.scheduleplus.presentation.adapters.schedule

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sbapps.scheduleplus.R

class ScheduleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textViewTitle: TextView = view.findViewById(R.id.textViewTitle)
    val recyclerViewSchedule: RecyclerView = view.findViewById(R.id.recyclerViewSchedule)
}