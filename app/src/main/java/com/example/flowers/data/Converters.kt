package com.example.flowers.data

import androidx.room.TypeConverter
import java.util.*

/**
 * Type converters to allow Room to reference complex data types.
 */
class Converters {
    // 日期转毫秒值
    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long = calendar.timeInMillis

    // 毫秒值转日期
    @TypeConverter
    fun datestampToCalendar(value: Long) : Calendar = Calendar.getInstance().apply {
        timeInMillis = value
    }
}