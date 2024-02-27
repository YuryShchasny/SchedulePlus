package com.sbapps.scheduleplus.domain.repository

import androidx.lifecycle.LiveData
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.entity.Week

interface ScheduleRepository {
    suspend fun addScheduleItem(scheduleItem: ScheduleItem)

    suspend fun deleteScheduleItem(scheduleItem: ScheduleItem)

    suspend fun editScheduleItem(scheduleItem: ScheduleItem)
    fun getScheduleItemList(): LiveData<List<ScheduleItem>>

    suspend fun addWeek(week: Week)

    suspend fun deleteWeek(week: Week)

    suspend fun editWeek(week: Week)

    fun getWeekList(): LiveData<List<Week>>

    suspend fun getWeek(weekId: Int): Week

   suspend fun setWeekActive(week: Week)

}