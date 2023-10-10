package com.example.templatekotlin1.common


import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.ISODateTimeFormat


object DateTimeUtil {

    fun getTimeOnly(date: String?): String? {
        try {
            if (date == null || date.isEmpty()) {
                return ""
            }
            //        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            val dt: DateTime = ISODateTimeFormat.dateTimeParser().parseDateTime(date)
            val dtfOut: DateTimeFormatter = DateTimeFormat.forPattern("HH:mm:ss")
            // Print the date
            System.out.println(dtfOut.print(dt))
            return dtfOut.print(dt)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }

    }

    fun getDateOnly(date: String?): String? {
        try {
            if (date == null || date.isEmpty()) {
                return ""
            }
            //        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            val dt: DateTime = ISODateTimeFormat.dateTimeParser().parseDateTime(date)
            val dtfOut: DateTimeFormatter = DateTimeFormat.forPattern("dd-MM-yyyy")
            // Print the date
            System.out.println(dtfOut.print(dt))
            return dtfOut.print(dt)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }

    }
    fun getCurrentDate(): String? {
        val localDate = LocalDate()
        return localDate.toString("dd-MM-yyyy")
    }
    fun convertDateTimeToServerFormat(date: String?): String {
        try {
            if (date == null || date.isEmpty()) {
                return ""
            }
// Format for input
            val dtf = DateTimeFormat.forPattern("dd-MM-yyyy")
// Parsing the date
            val jodatime = dtf.parseDateTime(date)
// Format for output
            val dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
// Printing the date
            println(dtfOut.print(jodatime))
            return dtfOut.print(jodatime)
        }catch (ex:Exception){
            ex.printStackTrace()
            return ""+date
        }
    }
}
