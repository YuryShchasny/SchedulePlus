package com.sbapps.scheduleplus.domain.usecases

import com.sbapps.scheduleplus.domain.repository.ScheduleRepository
import com.sbapps.scheduleplus.domain.entity.Week

class DeleteWeekUseCase(private val scheduleRepository: ScheduleRepository) {
    suspend operator fun invoke(week: Week) {
        scheduleRepository.deleteWeek(week)
    }
}