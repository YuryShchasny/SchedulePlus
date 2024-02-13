package com.sbapps.scheduleplus.presentation.adapters.dayofweek

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sbapps.scheduleplus.R
import com.sbapps.scheduleplus.domain.entity.DayOfWeek
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.entity.Week
import com.sbapps.scheduleplus.presentation.adapters.scheduleitem.ScheduleItemListAdapter

class DayOfWeekListAdapter(private val context: Context, private val weekList: List<Week>) :
    RecyclerView.Adapter<DayOfWeekViewHolder>() {

    private val daysOfWeek = mutableListOf<DayOfWeek>()

    init {
        for (week in weekList) {
            for (schedule in week.scheduleItemsList) {
                if (!daysOfWeek.contains(schedule.dayOfWeek)) {
                    daysOfWeek.add(schedule.dayOfWeek)
                }
            }
        }
        daysOfWeek.sortBy { it.ordinal }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayOfWeekViewHolder {
        val inflater = LayoutInflater.from(context)
        return DayOfWeekViewHolder(inflater.inflate(R.layout.day_of_week_item, parent, false))
    }

    override fun getItemCount(): Int {
        return daysOfWeek.size
    }

    override fun onBindViewHolder(holder: DayOfWeekViewHolder, position: Int) {
        val dayOfWeek = daysOfWeek[position]
        holder.textViewDayOfWeek.text =
            ContextCompat.getString(context, dayOfWeek.getStringResourceId())
        var scheduleItemList = mutableListOf<ScheduleItem>()
        for (week in weekList) {
            for (scheduleItem in week.scheduleItemsList) {
                if (scheduleItem.dayOfWeek == dayOfWeek) {
                    scheduleItemList.add(scheduleItem)
                }
            }
        }
        scheduleItemList = scheduleItemList.toSet().toMutableList()
        val sortedList = scheduleItemList.sortedBy { ScheduleItem.getTimeAsMinutes(it.startTime) }
        val adapter = ScheduleItemListAdapter(context, weekList)
        adapter.submitList(sortedList)
        holder.recyclerViewScheduleItem.adapter = adapter
    }
}