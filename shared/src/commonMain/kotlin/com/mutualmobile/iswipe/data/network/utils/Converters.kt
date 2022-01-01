package com.mutualmobile.iswipe.data.network.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Int.millisToTextualDayDate(timeZoneOffset: Int?): Pair<String, String> {
    val localDateTimeFromMillis = Instant.fromEpochSeconds(this.toLong(), timeZoneOffset ?: 0).toLocalDateTime(TimeZone.UTC)
    return Pair(localDateTimeFromMillis.dayOfWeek.name, "${localDateTimeFromMillis.month.name} ${localDateTimeFromMillis.date.dayOfMonth}, ${localDateTimeFromMillis.year}")
}
