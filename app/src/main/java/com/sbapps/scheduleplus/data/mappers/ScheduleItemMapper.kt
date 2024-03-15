package com.sbapps.scheduleplus.data.mappers

import com.sbapps.scheduleplus.data.ScheduleItemDbModel
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import javax.inject.Inject

class ScheduleItemMapper @Inject constructor() {
    fun mapEntityToDbModel(scheduleItem: ScheduleItem): ScheduleItemDbModel = ScheduleItemDbModel(
        id = scheduleItem.id,
        name = scheduleItem.name,
        startTime = scheduleItem.startTime,
        endTime = scheduleItem.endTime,
        dayOfWeek = scheduleItem.dayOfWeek,
        place = scheduleItem.place,
        color = scheduleItem.color,
        weekId = scheduleItem.weekId
    )

    fun mapDbModelToEntity(scheduleItemDbModel: ScheduleItemDbModel): ScheduleItem = ScheduleItem(
        id = scheduleItemDbModel.id,
        name = scheduleItemDbModel.name,
        startTime = scheduleItemDbModel.startTime,
        endTime = scheduleItemDbModel.endTime,
        dayOfWeek = scheduleItemDbModel.dayOfWeek,
        place = scheduleItemDbModel.place,
        color = scheduleItemDbModel.color,
        weekId = scheduleItemDbModel.weekId
    )

    fun mapListDbModelToListEntity(listDbModel: List<ScheduleItemDbModel>) = listDbModel.map {
        mapDbModelToEntity(it)
    }
}