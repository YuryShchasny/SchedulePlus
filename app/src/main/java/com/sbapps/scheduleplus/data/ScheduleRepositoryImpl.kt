package com.sbapps.scheduleplus.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.sbapps.scheduleplus.data.mappers.WeekMapper
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.entity.Week
import com.sbapps.scheduleplus.domain.repository.ScheduleRepository

class ScheduleRepositoryImpl(application: Application) : ScheduleRepository {

    private val weekDao = AppDatabase.getDatabase(application).weekDao()
    private val weekMapper = WeekMapper()


    override suspend fun addScheduleItem(weekId: Int, scheduleItem: ScheduleItem) {
        val week = getWeek(weekId)
        val scheduleItemList = getWeek(weekId).scheduleItemsList.toMutableList()
        for (i in 0..scheduleItemList.size) {
            if (!scheduleItemList.any { it.id == i }) {
                scheduleItem.id = i
            }
        }
        scheduleItemList.add(scheduleItem)
        week.scheduleItemsList = scheduleItemList
        editWeek(week)
    }

    override suspend fun deleteScheduleItem(weekId: Int, scheduleItem: ScheduleItem) {
        val week = getWeek(weekId)
        val scheduleItemList = getWeek(weekId).scheduleItemsList.toMutableList()
        scheduleItemList.remove(scheduleItem)
        week.scheduleItemsList = scheduleItemList
        editWeek(week)
    }

    override suspend fun addWeek(week: Week) {
        weekDao.insertWeek(weekMapper.mapEntityToDbModel(week))
    }

    override suspend fun deleteWeek(week: Week) {
        weekDao.deleteWeek(weekMapper.mapEntityToDbModel(week))
    }

    override suspend fun editWeek(week: Week) {
        weekDao.insertWeek(weekMapper.mapEntityToDbModel(week))
    }

    override fun getWeekList(): LiveData<List<Week>> {
        return weekDao.getAllWeeks().map {
            weekMapper.mapListDbModelToListEntity(it)
        }
    }

    override suspend fun getWeek(weekId: Int): Week {
        val dbModel = weekDao.getWeekById(weekId)
        return weekMapper.mapDbModelToEntity(dbModel)
    }

    override suspend fun setWeekActive(week: Week) {
        weekDao.getActiveWeek()?.let {
            val activeWeek = weekMapper.mapDbModelToEntity(it)
            activeWeek.isActive = false
            editWeek(activeWeek)
        }
        week.isActive = true
        editWeek(week)
    }

    override suspend fun editScheduleItem(weekId: Int, scheduleItem: ScheduleItem) {
        val week = getWeek(weekId)
        val scheduleItemList = getWeek(weekId).scheduleItemsList.toMutableList()
        scheduleItemList.removeIf { it.id == scheduleItem.id }
        scheduleItemList.add(scheduleItem)
        week.scheduleItemsList = scheduleItemList
        editWeek(week)
    }

}