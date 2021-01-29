package com.natashaval.moodpod.repository

import com.natashaval.moodpod.api.MoodApi
import com.natashaval.moodpod.model.Mood
import com.natashaval.moodpod.model.MyResponse
import com.natashaval.moodpod.utils.ResponseUtils
import javax.inject.Inject

/**
 * Created by natasha.santoso on 13/01/21.
 */
class MoodRepository @Inject constructor(private val api: MoodApi) {
  fun getMoodText(): String {
    return "I am happy now!"
  }

  suspend fun getMoods(): MyResponse<List<Mood>> {
    val response = api.getMoods()
    return ResponseUtils.convert(response)
  }

  suspend fun saveMood(mood: Mood?): MyResponse<Mood> {
    val response = api.saveMood(mood)
    return ResponseUtils.convert(response)
  }
}