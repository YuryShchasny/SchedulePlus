package com.sbapps.scheduleplus.presentation.adapters.dayofweek

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sbapps.scheduleplus.R

class DayOfWeekViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textViewDayOfWeek: TextView = view.findViewById(R.id.textViewDayOfWeek)
    val recyclerViewScheduleItem: RecyclerView = view.findViewById(R.id.recyclerViewScheduleItem)
}