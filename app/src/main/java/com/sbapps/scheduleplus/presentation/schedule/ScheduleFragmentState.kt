package com.sbapps.scheduleplus.presentation.schedule

import com.sbapps.scheduleplus.domain.entity.ScheduleItem
import com.sbapps.scheduleplus.domain.entity.Week

sealed class ScheduleFragmentState {

    data object Loading : ScheduleFragmentState()

    data class Error(val msg: String) : ScheduleFragmentState()

    data class Content(
        val currencyWeekList: List<Week>,
        val currencyScheduleItemList: List<ScheduleItem>
    ) : ScheduleFragmentState()
}