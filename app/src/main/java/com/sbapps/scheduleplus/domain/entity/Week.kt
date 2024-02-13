package com.sbapps.scheduleplus.domain.entity

data class Week(
    var isActive: Boolean,
    var scheduleItemsList: List<ScheduleItem>,
    var id: Int = UNDEFINED_ID,
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
