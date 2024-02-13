package com.sbapps.scheduleplus.presentation.adapters.editweek

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.sbapps.scheduleplus.R
import com.sbapps.scheduleplus.domain.entity.DayOfWeek
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.entity.Week
import com.sbapps.scheduleplus.presentation.adapters.scheduleitem.ScheduleItemListAdapter

class EditWeekAdapter(private val context: Context, private val week: Week) :
    RecyclerView.Adapter<EditWeekViewHolder>() {


    private val daysOfWeek = mutableListOf<DayOfWeek>()

    init {
        for (schedule in week.scheduleItemsList) {
            if (!daysOfWeek.contains(schedule.dayOfWeek)) {
                daysOfWeek.add(schedule.dayOfWeek)
            }
        }
        daysOfWeek.sortBy { it.ordinal }
    }

    var onScheduleItemClickListener: ((ScheduleItem) -> Unit)? = null
    var onScheduleItemSwiped: ((ScheduleItem) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditWeekViewHolder {
        val inflater = LayoutInflater.from(context)
        return EditWeekViewHolder(inflater.inflate(R.layout.edit_week_item, parent, false))
    }

    override fun getItemCount(): Int {
        return daysOfWeek.size
    }

    override fun onBindViewHolder(holder: EditWeekViewHolder, position: Int) {
        val dayOfWeek = daysOfWeek[position]
        holder.textViewDayOfWeek.text =
            ContextCompat.getString(context, dayOfWeek.getStringResourceId())
        val scheduleItemList = mutableListOf<ScheduleItem>()
        for (scheduleItem in week.scheduleItemsList) {
            if (scheduleItem.dayOfWeek == dayOfWeek) {
                scheduleItemList.add(scheduleItem)
            }
        }
        val adapter = ScheduleItemListAdapter(context, listOf(week), false)
        onScheduleItemSwiped?.let {
            val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val item = adapter.currentList[viewHolder.adapterPosition]
                    it.invoke(item)
                }

            }
            val itemTouchHelper = ItemTouchHelper(callback)
            itemTouchHelper.attachToRecyclerView(holder.recyclerViewScheduleItem)
        }
        val sortedList = scheduleItemList.sortedBy { ScheduleItem.getTimeAsMinutes(it.startTime) }
        adapter.submitList(sortedList)
        adapter.onScheduleItemClickListener = onScheduleItemClickListener
        holder.recyclerViewScheduleItem.adapter = adapter
    }
}