package com.sbapps.scheduleplus.domain.usecases.week

import com.sbapps.scheduleplus.domain.entity.Week
import com.sbapps.scheduleplus.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeekListUseCase @Inject constructor(private val scheduleRepository: ScheduleRepository) {
    operator fun invoke(): Flow<List<Week>> = scheduleRepository.getWeekList()
}