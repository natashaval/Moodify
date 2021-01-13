package com.natashaval.moodpod.util

import android.view.View
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
}