package com.sbapps.scheduleplus.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertScheduleItem(scheduleItemDbModel: ScheduleItemDbModel)

    @Query("SELECT * FROM schedule_items")
    fun getAllScheduleItems(): Flow<List<ScheduleItemDbModel>>

    @Delete
    suspend fun deleteScheduleItem(scheduleItemDbModel: ScheduleItemDbModel)

    @Query("DELETE FROM schedule_items WHERE weekId = :weekId")
    suspend fun deleteAllScheduleItemsByWeek(weekId: Int)

}