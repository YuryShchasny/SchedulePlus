package com.sbapps.scheduleplus.domain.usecases.scheduleitem

import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScheduleItemListUseCase @Inject constructor(private val scheduleRepository: ScheduleRepository) {
    operator fun invoke(): Flow<List<ScheduleItem>> = scheduleRepository.getScheduleItemList()
}