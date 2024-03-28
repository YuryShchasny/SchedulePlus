package com.sbapps.scheduleplus.presentation.main

import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.entity.Week

sealed class ScheduleMainState {
    data object Loading : ScheduleMainState()

    data class Error(val msg: String) : ScheduleMainState()

    data class Content(
        val currencyWeekList: List<Week>,
        val currencyScheduleItemList: List<ScheduleItem>
    ) : ScheduleMainState()
}