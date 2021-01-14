package com.natashaval.moodpod.repository

import com.natashaval.moodpod.api.AffirmationApi
import com.natashaval.moodpod.api.QuoteApi
import com.natashaval.moodpod.model.Affirmation
import com.natashaval.moodpod.model.MyResponse
import com.natashaval.moodpod.model.Quote
import com.natashaval.moodpod.utils.ResponseUtils
import javax.inject.Inject

/**
 * Created by natasha.santoso on 14/01/21.
 */
class AffirmationRepository @Inject constructor(private val api: AffirmationApi,
    private val quoteApi: QuoteApi) {
  suspend fun getAffirmation(): MyResponse<Affirmation> {
    val response = api.getAffirmation()
    return ResponseUtils.convert(response)
  }

  suspend fun getQuoteToday(): MyResponse<Quote> {
    val response = quoteApi.getQuoteToday()
    try {
      return if (response.isSuccessful) {
        val quote = (response.body() as List<Quote>)[0]
        MyResponse.success(quote)
      } else {
        MyResponse.failed(response.errorBody().toString())
      }
    }catch (e: Exception) {
      return MyResponse.error(null, e.message)
    }
  }
}