package com.natashaval.moodpod.utils

import android.view.View
import com.jakewharton.rxbinding4.view.clicks
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by natasha.santoso on 12/01/21.
 */
object ViewUtils {
  fun View.setSafeClickListener(block: () -> Unit) {
    this.clicks().throttleFirst(500L, TimeUnit.MILLISECONDS).subscribe {
      block()
    }
  }

  fun View.showView() {
    this.visibility = View.VISIBLE
  }

  fun View.hideView() {
    this.visibility = View.GONE
  }

//  https://stackoverflow.com/questions/454315/how-to-format-date-and-time-in-android
  fun Date.convertDate(): String {
    val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
    return dateFormat.format(this)
  }

  fun Date.convertTime(): String {
    val timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.ENGLISH)
    return timeFormat.format(this)
  }

}