package com.natashaval.moodpod.utils

import com.natashaval.moodpod.model.MyResponse
import retrofit2.Response
import timber.log.Timber

/**
 * Created by natasha.santoso on 14/01/21.
 */
object ResponseUtils {
  fun <T> convert(response: Response<in T>): MyResponse<T> {
    return try {
      if (response.isSuccessful) {
        MyResponse.success(response.body() as T)
      } else {
        Timber.e("Logging response failed: ${response.errorBody()}")
        MyResponse.failed(response.errorBody().toString())
      }
    } catch (e: Exception) {
      Timber.e("Logging response error: ${e.message}")
      MyResponse.error(null, e.message)
    }
  }
}