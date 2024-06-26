package com.sbapps.scheduleplus.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weeks")
data class WeekDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val isActive: Boolean
)
