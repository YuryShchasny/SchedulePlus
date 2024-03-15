package com.sbapps.scheduleplus.domain.usecases.scheduleitem

import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.repository.ScheduleRepository
import javax.inject.Inject

class AddScheduleItemUseCase @Inject constructor(private val scheduleRepository: ScheduleRepository) {
    suspend operator fun invoke(scheduleItem: ScheduleItem) {
        scheduleRepository.addScheduleItem(scheduleItem)
    }
}