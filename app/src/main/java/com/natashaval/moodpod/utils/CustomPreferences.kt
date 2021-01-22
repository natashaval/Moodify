package com.natashaval.moodpod.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by natasha.santoso on 22/01/21.
 */
//https://stackoverflow.com/questions/63643065/the-best-way-to-wrap-sharedpreference-object-in-hilt

@Singleton
class CustomPreferences @Inject constructor(@ApplicationContext context: Context) {
  companion object {
    private const val SHARED_PREFERENCES_NAME = "moodpod"
  }

  private val sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME,
      Context.MODE_PRIVATE)
  private val editor = sharedPreferences.edit()

  fun putString(key: String, value: String?) {
    editor.putString(key, value).apply()
  }

  fun putInt(key: String, value: Int = 0) {
    editor.putInt(key, value).apply()
  }

  fun putBoolean(key: String, value: Boolean = false) {
    editor.putBoolean(key, value).apply()
  }

  fun getString(key: String): String {
    return sharedPreferences.getString(key, "") ?: ""
  }

  fun getInt(key: String): Int {
    return sharedPreferences.getInt(key, 0)
  }

  fun getBoolean(key: String): Boolean {
    return sharedPreferences.getBoolean(key, false)
  }

  fun containKey(key: String): Boolean {
    return sharedPreferences.contains(key)
  }

  fun clearAll() {
    editor.clear().apply()
  }
}