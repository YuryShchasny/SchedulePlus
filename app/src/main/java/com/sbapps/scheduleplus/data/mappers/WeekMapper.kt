package com.sbapps.scheduleplus.data.mappers

import com.sbapps.scheduleplus.data.WeekDbModel
import com.sbapps.scheduleplus.domain.entity.Week

class WeekMapper {
    fun mapEntityToDbModel(week: Week): WeekDbModel = WeekDbModel(
        id = week.id,
        isActive = week.isActive
    )

    fun mapDbModelToEntity(weekDbModel: WeekDbModel): Week = Week(
        id = weekDbModel.id,
        isActive = weekDbModel.isActive
    )

    fun mapListDbModelToListEntity(listDbModel: List<WeekDbModel>) = listDbModel.map {
        mapDbModelToEntity(it)
    }

}