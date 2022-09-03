package com.example.core_database_data.database.room.typeConverters

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class TimestampConverter {

    private val pattern = "yyyy-MM-dd'T'HH:mm:ss"

    @TypeConverter
    fun fromStringToDate(date:String):Date {
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val localDate = LocalDate.parse(date,formatter)
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
    }

    @TypeConverter
    fun fromDateToString(date: Date):String {
        val formatter = SimpleDateFormat(pattern, Locale.getDefault())
        return formatter.format(date)
    }
}