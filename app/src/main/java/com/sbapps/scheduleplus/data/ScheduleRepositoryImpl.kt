package com.sbapps.scheduleplus.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.sbapps.scheduleplus.data.mappers.ScheduleItemMapper
import com.sbapps.scheduleplus.data.mappers.WeekMapper
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.entity.Week
import com.sbapps.scheduleplus.domain.repository.ScheduleRepository

class ScheduleRepositoryImpl(application: Application) : ScheduleRepository {

    private val weekDao = AppDatabase.getDatabase(application).weekDao()
    private val weekMapper = WeekMapper()
    private val scheduleItemDao = AppDatabase.getDatabase(application).scheduleItemDao()
    private val scheduleItemMapper = ScheduleItemMapper()


    override suspend fun addScheduleItem(scheduleItem: ScheduleItem) {
        scheduleItemDao.insertScheduleItem(scheduleItemMapper.mapEntityToDbModel(scheduleItem))
    }

    override suspend fun deleteScheduleItem(scheduleItem: ScheduleItem) {
        scheduleItemDao.deleteScheduleItem(scheduleItemMapper.mapEntityToDbModel(scheduleItem))
    }

    override suspend fun editScheduleItem(scheduleItem: ScheduleItem) {
        scheduleItemDao.insertScheduleItem(scheduleItemMapper.mapEntityToDbModel(scheduleItem))
    }

    override fun getScheduleItemList(): LiveData<List<ScheduleItem>> {
        return scheduleItemDao.getAllScheduleItems().map {
            scheduleItemMapper.mapListDbModelToListEntity(it)
        }
    }

    override suspend fun addWeek(week: Week) {
        weekDao.insertWeek(weekMapper.mapEntityToDbModel(week))
    }

    override suspend fun deleteWeek(week: Week) {
        weekDao.deleteWeek(weekMapper.mapEntityToDbModel(week))
        scheduleItemDao.deleteAllScheduleItemsByWeek(week.id)
    }

    override fun getWeekList(): LiveData<List<Week>> {
        return weekDao.getAllWeeks().map {
            weekMapper.mapListDbModelToListEntity(it)
        }
    }

    override suspend fun getWeek(weekId: Int): Week {
        return weekMapper.mapDbModelToEntity(weekDao.getWeekById(weekId))
    }

    override suspend fun setWeekActive(week: Week) {
        weekDao.getActiveWeek()?.let {
            val activeWeek = weekMapper.mapDbModelToEntity(it)
            val activeWeekEdit = activeWeek.copy(isActive = false)
            editWeek(activeWeekEdit)
        }
        editWeek(week.copy(isActive = true))
    }
    override suspend fun editWeek(week: Week) {
        weekDao.insertWeek(weekMapper.mapEntityToDbModel(week))
    }

}