package com.natashaval.moodpod

import android.util.Log

/**
 * Created by natasha.santoso on 05/07/21.
 */
class WhatATerribleFailure {
  fun <T> logAsWtf(clazz: Class<T>, message: String) {
    Log.wtf(clazz.name, message)

    wtf(message)
  }

  fun wtf(message: String) {
    Log.d("TAG", message)
  }
}