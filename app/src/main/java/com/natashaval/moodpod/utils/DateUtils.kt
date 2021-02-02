package com.natashaval.moodpod.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by natasha.santoso on 02/02/21.
 */
object DateUtils {

  //  https://stackoverflow.com/questions/454315/how-to-format-date-and-time-in-android
  fun Date.convertDate(): String {
    val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK)
    return dateFormat.format(this)
  }

  fun Long.convertDate(): String {
    val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.UK)
    return dateFormat.format(this)
  }

  fun Date.convertTime(): String {
    val timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.UK)
    return timeFormat.format(this)
  }

  fun Date.convertDateTime(): String {
    val dateTimeFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, Locale.UK)
    return dateTimeFormat.format(this)
  }

  fun Date.convertISODate(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    return formatter.format(this)
  }

  fun String.parseISODate(): Date? {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    return formatter.parse(this)
  }

  fun Date.dateToCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar
  }
}