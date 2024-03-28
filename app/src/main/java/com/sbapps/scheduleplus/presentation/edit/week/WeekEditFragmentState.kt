package com.sbapps.scheduleplus.presentation.edit.week

import com.sbapps.scheduleplus.domain.entity.ScheduleItem

sealed class WeekEditFragmentState {
    data object Loading : WeekEditFragmentState()

    data class ScheduleItemEditDialog(val scheduleItem: ScheduleItem) : WeekEditFragmentState()

    data class Error(val msg: String) : WeekEditFragmentState()

    data class Content(
        val currencyScheduleItemList: List<ScheduleItem>
    ) : WeekEditFragmentState()
}