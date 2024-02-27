package com.sbapps.scheduleplus.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sbapps.scheduleplus.domain.entity.DayOfWeek

@Entity(tableName = "schedule_items")
class ScheduleItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val startTime: String,
    val endTime: String,
    val dayOfWeek: DayOfWeek,
    val place: String,
    val color: Int,
    val weekId: Int
)