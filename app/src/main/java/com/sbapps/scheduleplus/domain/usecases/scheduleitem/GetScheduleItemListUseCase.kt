package com.sbapps.scheduleplus.domain.usecases.scheduleitem

import androidx.lifecycle.LiveData
import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.repository.ScheduleRepository
import javax.inject.Inject

class GetScheduleItemListUseCase @Inject constructor(private val scheduleRepository: ScheduleRepository) {
    operator fun invoke(): LiveData<List<ScheduleItem>> = scheduleRepository.getScheduleItemList()
}