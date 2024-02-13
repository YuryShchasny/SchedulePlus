package com.sbapps.scheduleplus.domain.usecases

import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.repository.ScheduleRepository

class EditScheduleItemUseCase(private val scheduleRepository: ScheduleRepository) {
    suspend operator fun invoke(weekId: Int, scheduleItem: ScheduleItem) {
        scheduleRepository.editScheduleItem(weekId, scheduleItem)
    }
}