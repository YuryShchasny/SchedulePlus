package com.sbapps.scheduleplus.presentation.adapters.week

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.sbapps.scheduleplus.R
import com.sbapps.scheduleplus.domain.entity.Week

class WeekListAdapter(private val context: Context) : ListAdapter<Week, WeekViewHolder>(
    WeekItemDiffCallback()
) {

    var onWeekLongClickListener: ((Week) -> Unit)? = null

    var onWeekClickListener: ((Week) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder {
        val inflater = LayoutInflater.from(context)
        return WeekViewHolder(inflater.inflate(R.layout.week_item, parent, false))
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) {
        val currentWeek = getItem(position)
        holder.textViewWeek.text = ContextCompat.getString(
            context,
            R.string.week
        ) + " ${currentList.indexOf(currentWeek) + 1}"
        holder.viewActiveWeekColorShape.backgroundTintList = if (currentWeek.isActive) {
            ColorStateList.valueOf(getColorFromTheme(com.google.android.material.R.attr.colorPrimary))
        } else {
            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorNotActive))
        }
        holder.itemView.setOnLongClickListener {
            onWeekLongClickListener?.invoke(currentWeek)
            true
        }
        holder.itemView.setOnClickListener {
            onWeekClickListener?.invoke(currentWeek)
        }
    }

    private fun getColorFromTheme(colorAttribute: Int): Int {
        val typedValue = TypedValue()
        val theme = context.theme
        theme.resolveAttribute(colorAttribute, typedValue, true)
        return typedValue.data
    }
}