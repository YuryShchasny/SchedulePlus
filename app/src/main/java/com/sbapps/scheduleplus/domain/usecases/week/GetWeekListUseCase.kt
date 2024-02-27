package com.sbapps.scheduleplus.domain.usecases.week

import androidx.lifecycle.LiveData
import com.sbapps.scheduleplus.domain.repository.ScheduleRepository
import com.sbapps.scheduleplus.domain.entity.Week

class GetWeekListUseCase(private val scheduleRepository: ScheduleRepository) {
    operator fun invoke(): LiveData<List<Week>> = scheduleRepository.getWeekList()
}