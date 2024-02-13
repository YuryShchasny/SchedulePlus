package com.sbapps.scheduleplus.presentation.adapters.schedule

import android.annotation.SuppressLint
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ScheduleAdapter(private val context: Context, private var weekList: List<Week>) :
    RecyclerView.Adapter<ScheduleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val inflater = LayoutInflater.from(context)
        return ScheduleViewHolder(inflater.inflate(R.layout.schedule_item, parent, false))
    }

    private var allDaysOfWeek = mutableListOf<DayOfWeekWithNumberAndDate>()

    init {
        var activeWeek = 0

        weekList.forEachIndexed { index, week ->
            if (week.isActive) {
                activeWeek = index
            }
            val daysOfWeek = mutableListOf<DayOfWeek>()
            for (scheduleItem in week.scheduleItemsList) {
                if (!daysOfWeek.contains(scheduleItem.dayOfWeek)) {
                    daysOfWeek.add(scheduleItem.dayOfWeek)
                }
            }
            daysOfWeek.sortBy { it.ordinal }
            for (dayOfWeek in daysOfWeek) {
                allDaysOfWeek.add(DayOfWeekWithNumberAndDate(dayOfWeek, index))
            }
        }
        val currentDate = Calendar.getInstance()
        val currentDayOfWeekValue = currentDate.get(Calendar.DAY_OF_WEEK) - 2
        val currentLocale = Locale.getDefault()
        val dateFormat = SimpleDateFormat("dd MMMM", currentLocale)
        allDaysOfWeek = allDaysOfWeek.filter { dayOfWeek ->
            dayOfWeek.number != activeWeek || dayOfWeek.dayOfWeek.ordinal >= currentDayOfWeekValue
        }.toMutableList()
        allDaysOfWeek = allDaysOfWeek.sortedWith(compareByDescending { it.number == activeWeek })
            .toMutableList()
        var currentWeek = 0
        weekList.find { it.isActive }?.let {
            currentWeek = weekList.indexOf(it)
        }
        var currentDayOfWeek = currentDayOfWeekValue
        for (dayOfWeek in allDaysOfWeek) {
            while (currentDayOfWeek != dayOfWeek.dayOfWeek.ordinal || currentWeek != dayOfWeek.number) {
                currentDayOfWeek = (currentDayOfWeek + 1) % 7
                currentDate.add(Calendar.DAY_OF_MONTH, 1)
                if (currentDayOfWeek == 0) currentWeek =
                    (currentWeek + 1) % weekList.size
            }
            val formattedDate = dateFormat.format(currentDate.time)
            dayOfWeek.date = formattedDate
            currentDate.add(Calendar.DAY_OF_MONTH, 1)
            currentDayOfWeek = (currentDayOfWeek + 1) % 7
            currentWeek = dayOfWeek.number
        }
    }

    override fun getItemCount(): Int {
        return allDaysOfWeek.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val dayOfWeek = allDaysOfWeek[position]
        holder.textViewTitle.text =
            ContextCompat.getString(context, dayOfWeek.dayOfWeek.getStringResourceId()) +
                    ", ${dayOfWeek.date}" +
                    ", ${
                        ContextCompat.getString(
                            context,
                            R.string.week
                        )
                    } ${dayOfWeek.number + 1}"
        var scheduleItemList = mutableListOf<ScheduleItem>()
        for (scheduleItem in weekList[dayOfWeek.number].scheduleItemsList) {
            if (scheduleItem.dayOfWeek == dayOfWeek.dayOfWeek) {
                scheduleItemList.add(scheduleItem)
            }
        }
        val adapter = ScheduleItemListAdapter(context, null, false)
        scheduleItemList = scheduleItemList.toSet().toMutableList()
        val sortedList = scheduleItemList.sortedBy { ScheduleItem.getTimeAsMinutes(it.startTime) }
        adapter.submitList(sortedList)
        holder.recyclerViewSchedule.adapter = adapter
    }
}