package com.sbapps.scheduleplus.presentation.adapters.scheduleitem

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.sbapps.scheduleplus.R
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.entity.Week

class ScheduleItemListAdapter(
    private val context: Context,
    private val weekList: List<Week>?, //null if showNumbersOfWeeks == false
    private val showNumbersOfWeeks: Boolean = true
) : ListAdapter<ScheduleItem, ScheduleItemViewHolder>(ScheduleItemListDiffCallback()) {


    var onScheduleItemClickListener: ((ScheduleItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleItemViewHolder {
        val inflater = LayoutInflater.from(context)
        return ScheduleItemViewHolder(inflater.inflate(R.layout.schedule_item_item, parent, false))
    }


    override fun onBindViewHolder(holder: ScheduleItemViewHolder, position: Int) {
        val currentSchedule = getItem(position)
        holder.textViewStartTime.text = currentSchedule.startTime
        holder.textViewEndTime.text = currentSchedule.endTime
        holder.textViewName.text = currentSchedule.name
        holder.textViewPlace.text = currentSchedule.place
        holder.viewColorShape.backgroundTintList = ColorStateList.valueOf(currentSchedule.color)

        if (!showNumbersOfWeeks) {
            holder.textViewNumbersOfWeek.text = ""
            holder.imageViewIcon.visibility = View.GONE
        } else {
            getNumbersOfWeek(holder, currentSchedule)
        }
        onScheduleItemClickListener?.let { listener ->
            holder.itemView.setOnClickListener {
                listener.invoke(currentSchedule)
            }
        }
    }

    private fun getNumbersOfWeek(holder: ScheduleItemViewHolder, currentSchedule: ScheduleItem) {
        weekList?.let {
            val numbersOfWeeks = mutableListOf<Int>()
            for (week in weekList) {
                for (schedule in week.scheduleItemsList) {
                    if (schedule == currentSchedule) {
                        numbersOfWeeks.add(weekList.indexOf(week) + 1)
                        break
                    }
                }
            }
            if (numbersOfWeeks.size == weekList.size) {
                holder.textViewNumbersOfWeek.text = "∞"
                holder.imageViewIcon.visibility = View.VISIBLE
            } else {
                holder.imageViewIcon.visibility = View.VISIBLE
                var numbersOfWeekText = ""
                for (numberOfWeek in numbersOfWeeks) {
                    numbersOfWeekText += "$numberOfWeek,"
                }
                holder.textViewNumbersOfWeek.text =
                    numbersOfWeekText.substring(0, numbersOfWeekText.length - 1) // without ','
            }
        }
    }
}