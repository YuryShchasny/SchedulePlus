package com.sbapps.scheduleplus.presentation.adapters.week

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sbapps.scheduleplus.R

class WeekViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val textViewWeek: TextView = view.findViewById(R.id.textViewWeek)
    val viewActiveWeekColorShape: View = view.findViewById(R.id.viewActiveWeekColorShape)

}