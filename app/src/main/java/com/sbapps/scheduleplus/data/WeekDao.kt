package com.sbapps.scheduleplus.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeekDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeek(week: WeekDbModel)

    @Query("SELECT * FROM weeks")
    fun getAllWeeks(): LiveData<List<WeekDbModel>>

    @Query("SELECT * FROM weeks WHERE id = :weekId LIMIT 1")
    suspend fun getWeekById(weekId: Int): WeekDbModel

    @Delete
    suspend fun deleteWeek(week: WeekDbModel)

    @Query("SELECT * FROM weeks WHERE isActive = 1 LIMIT 1")
    suspend fun getActiveWeek(): WeekDbModel?
}