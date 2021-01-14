package com.natashaval.moodpod.api

import com.natashaval.moodpod.model.Affirmation
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by natasha.santoso on 14/01/21.
 */
interface AffirmationApi {
  @GET(".")
  suspend fun getAffirmation(): Response<Affirmation>
}