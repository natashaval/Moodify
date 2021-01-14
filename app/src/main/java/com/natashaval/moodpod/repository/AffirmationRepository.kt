package com.natashaval.moodpod.repository

import com.natashaval.moodpod.api.AffirmationApi
import com.natashaval.moodpod.model.Affirmation
import com.natashaval.moodpod.model.MyResponse
import com.natashaval.moodpod.utils.ResponseUtils
import javax.inject.Inject

/**
 * Created by natasha.santoso on 14/01/21.
 */
class AffirmationRepository @Inject constructor(private val api: AffirmationApi) {
  suspend fun getAffirmation(): MyResponse<Affirmation> {
    val response = api.getAffirmation()
    return ResponseUtils.convert(response)
  }
}