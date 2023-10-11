package com.example.templatekotlin1.common.db.convertor

import androidx.room.TypeConverter
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.sql.Timestamp


class DateTypeConverters {
    @TypeConverter
    fun stringToDate(data: String?): DateTime {
        try{
            val timeZone = DateTimeZone.forID("Asia/Kolkata")
            val formatter: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSSZZ").withZone(timeZone)
//                .withLocale(Locale.ROOT)
//                .withChronology(ISOChronology.getInstanceUTC())

            return formatter.parseDateTime(data)
        }catch (e:Exception){
            e.printStackTrace()
            val timeZone = DateTimeZone.forID("Asia/Kolkata")
            val formatter: DateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSSZZ").withZone(timeZone)
//                .withLocale(Locale.ROOT)
//                .withChronology(ISOChronology.getInstanceUTC())

            return formatter.parseDateTime(data)
        }

    }

    @TypeConverter
    fun dateToString(dateTime: DateTime?): String {
        if(dateTime==null){
            return ""
        }
        val d=dateTime.millis
        val timestamp = Timestamp(dateTime.millis)
        return timestamp.toString()
    }
}