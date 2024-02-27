package com.sbapps.scheduleplus.domain.usecases.scheduleitem

import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.repository.ScheduleRepository

class EditScheduleItemUseCase(private val scheduleRepository: ScheduleRepository) {
    suspend operator fun invoke(scheduleItem: ScheduleItem) {
        scheduleRepository.editScheduleItem(scheduleItem)
    }
}