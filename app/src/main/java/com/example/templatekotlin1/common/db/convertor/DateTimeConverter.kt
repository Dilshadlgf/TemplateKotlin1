package com.example.templatekotlin1.common.db.convertor

import androidx.room.TypeConverter
import org.joda.time.DateTime

class DateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): DateTime? {
        return value?.let { DateTime(it) }
    }

    @TypeConverter
    fun dateToTimestamp(dateTime: DateTime?): Long? {
        return dateTime?.millis
    }
}