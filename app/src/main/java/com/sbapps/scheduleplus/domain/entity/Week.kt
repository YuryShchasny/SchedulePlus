package com.sbapps.scheduleplus.domain.entity

data class Week(
    val isActive: Boolean,
    val id: Int = UNDEFINED_ID,
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}
