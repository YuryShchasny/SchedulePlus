package com.sbapps.scheduleplus.domain.usecases

import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.repository.ScheduleRepository

class DeleteScheduleItemUseCase(private val scheduleRepository: ScheduleRepository) {
    suspend operator fun invoke(weekId: Int, scheduleItem: ScheduleItem) {
        scheduleRepository.deleteScheduleItem(weekId, scheduleItem)
    }
}