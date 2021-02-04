package com.natashaval.moodpod.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.annotation.AttrRes
import com.jakewharton.rxbinding4.view.clicks
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

//  https://github.com/material-components/material-components-android/blob/master/catalog/java/io/material/catalog/datepicker/DatePickerMainDemoFragment.java
//  get Material Date Range Picker theme
  fun resolveOrThrowAttr(context: Context, @AttrRes attributeResId: Int): Int {
    val typedValue = TypedValue()
    if (context.theme.resolveAttribute(attributeResId, typedValue, true)) {
      return typedValue.data
    }
    throw IllegalArgumentException(context.resources.getResourceName(attributeResId))
  }
}