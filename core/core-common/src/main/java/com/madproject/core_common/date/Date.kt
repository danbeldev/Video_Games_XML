package com.madproject.core_common.date

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

private const val pattern = "yyyy-MM-dd'T'HH:mm:ss"

fun getUserDateDevice():Date {
    return Calendar.getInstance().time
}

fun fromStringToDate(date:String):Date {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val localDate = LocalDate.parse(date,formatter)
    return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
}

fun fromDateToString(date: Date?):String {
    date ?: return ""
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(date)
}