package com.natashaval.moodpod.api

import com.natashaval.moodpod.model.Quote
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by natasha.santoso on 14/01/21.
 */
interface QuoteApi {
  @GET("/api/today")
  suspend fun getQuoteToday(): Response<List<Quote>>
}