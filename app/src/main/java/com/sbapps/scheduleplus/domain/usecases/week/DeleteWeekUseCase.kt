package com.sbapps.scheduleplus.domain.usecases.week

import com.sbapps.scheduleplus.domain.repository.ScheduleRepository
import com.sbapps.scheduleplus.domain.entity.Week
import javax.inject.Inject

class DeleteWeekUseCase @Inject constructor(private val scheduleRepository: ScheduleRepository) {
    suspend operator fun invoke(week: Week) {
        scheduleRepository.deleteWeek(week)
    }
}