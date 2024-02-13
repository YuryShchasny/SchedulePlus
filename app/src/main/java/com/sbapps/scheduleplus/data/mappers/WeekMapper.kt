package com.sbapps.scheduleplus.data.mappers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sbapps.scheduleplus.data.WeekDbModel
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.entity.Week

class WeekMapper {
    fun mapEntityToDbModel(week: Week): WeekDbModel = WeekDbModel(
        id = week.id,
        isActive = week.isActive,
        scheduleItemsList = Gson().toJson(week.scheduleItemsList)
    )

    fun mapDbModelToEntity(weekDbModel: WeekDbModel): Week = Week(
        id = weekDbModel.id,
        isActive = weekDbModel.isActive,
        scheduleItemsList = convertFromJson(weekDbModel.scheduleItemsList)
    )

    fun mapListDbModelToListEntity(listDbModel: List<WeekDbModel>) = listDbModel.map {
        mapDbModelToEntity(it)
    }

    private fun convertFromJson(json: String): List<ScheduleItem> {
        val gson = Gson()
        val listType = object : TypeToken<List<ScheduleItem>>() {}.type
        return gson.fromJson(json, listType)
    }
}