package com.natashaval.moodpod.repository

import javax.inject.Inject

/**
 * Created by natasha.santoso on 13/01/21.
 */
class MoodRepository @Inject constructor() {
  fun getMoodText(): String {
    return "I am happy now!"
  }
}