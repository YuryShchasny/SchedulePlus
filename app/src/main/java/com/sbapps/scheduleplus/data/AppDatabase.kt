package com.sbapps.scheduleplus.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WeekDbModel::class, ScheduleItemDbModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weekDao(): WeekDao
    abstract fun scheduleItemDao(): ScheduleItemDao

}