package com.sbapps.scheduleplus.data

import com.sbapps.scheduleplus.data.mappers.ScheduleItemMapper
import com.sbapps.scheduleplus.data.mappers.WeekMapper
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.entity.Week
import com.sbapps.scheduleplus.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val weekDao: WeekDao,
    private val weekMapper: WeekMapper,
    private val scheduleItemDao: ScheduleItemDao,
    private val scheduleItemMapper: ScheduleItemMapper
) : ScheduleRepository {

    override suspend fun addScheduleItem(scheduleItem: ScheduleItem) {
        scheduleItemDao.insertScheduleItem(scheduleItemMapper.mapEntityToDbModel(scheduleItem))
    }

    override suspend fun deleteScheduleItem(scheduleItem: ScheduleItem) {
        scheduleItemDao.deleteScheduleItem(scheduleItemMapper.mapEntityToDbModel(scheduleItem))
    }

    override suspend fun editScheduleItem(scheduleItem: ScheduleItem) {
        scheduleItemDao.insertScheduleItem(scheduleItemMapper.mapEntityToDbModel(scheduleItem))
    }

    override fun getScheduleItemList(): Flow<List<ScheduleItem>> {
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

    override fun getWeekList(): Flow<List<Week>> {
        return weekDao.getAllWeeks().map {
            weekMapper.mapListDbModelToListEntity(it)
        }
    }

    override suspend fun getWeek(weekId: Int): Week {
        return weekMapper.mapDbModelToEntity(weekDao.getWeekById(weekId))
    }

    override suspend fun setWeekActive(week: Week) {

        val activeWeek = weekDao.getActiveWeek()?.let {
            val activeWeek = weekMapper.mapDbModelToEntity(it)
            activeWeek.copy(isActive = false)
        }
        editWeek(week.copy(isActive = true))
        activeWeek?.let { editWeek(it) }
    }

    override suspend fun editWeek(week: Week) {
        weekDao.insertWeek(weekMapper.mapEntityToDbModel(week))
    }

}