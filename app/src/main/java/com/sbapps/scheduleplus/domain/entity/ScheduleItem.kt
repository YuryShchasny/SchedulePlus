package com.sbapps.scheduleplus.domain.entity

import android.graphics.Color

data class ScheduleItem(
    val name: String,
    val startTime: String = "12:00",
    val endTime: String = "14:00",
    val dayOfWeek: DayOfWeek = DayOfWeek.MONDAY,
    val place: String,
    val weekId: Int,
    val color: Int = STANDARD_COLOR,
    val id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
        const val STANDARD_COLOR = Color.GREEN

        fun getTimeAsMinutes(time: String): Int {
            val (hours, minutes) = time.split(":")
            return hours.toInt() * 60 + minutes.toInt()
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || javaClass != other.javaClass) return false

        other as ScheduleItem

        return name == other.name && place == other.place && startTime == other.startTime && endTime == other.endTime && dayOfWeek == other.dayOfWeek
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + startTime.hashCode()
        result = 31 * result + endTime.hashCode()
        result = 31 * result + dayOfWeek.hashCode()
        result = 31 * result + place.hashCode()
        return result
    }


}
