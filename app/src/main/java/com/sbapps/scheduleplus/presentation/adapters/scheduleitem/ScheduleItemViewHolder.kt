package com.sbapps.scheduleplus.presentation.adapters.scheduleitem

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sbapps.scheduleplus.R

class ScheduleItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textViewStartTime: TextView = view.findViewById(R.id.textViewStartTime)
    val textViewEndTime: TextView = view.findViewById(R.id.textViewEndTime)
    val textViewName: TextView = view.findViewById(R.id.textViewName)
    val textViewNumbersOfWeek: TextView = view.findViewById(R.id.textViewNumbersOfWeek)
    val textViewPlace: TextView = view.findViewById(R.id.textViewPlace)
    val viewColorShape: View = view.findViewById(R.id.viewColorShape)
    val imageViewIcon: View = view.findViewById(R.id.imageViewIcon)
}