package com.natashaval.moodpod.api

import com.natashaval.moodpod.model.Mood
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by natasha.santoso on 29/01/21.
 */
interface MoodApi {
  @GET("/mood")
  suspend fun getMoods(): Response<List<Mood>>

  @POST("/mood")
  suspend fun saveMood(@Body mood: Mood?): Response<Mood>

  @GET("/mood/{id}")
  suspend fun getMoodById(@Path("id") id: String): Response<Mood>

  @PUT("/mood/{id}")
  suspend fun updateMood(@Path("id") id: String, @Body mood: Mood?): Response<Mood>

  @DELETE("/mood/{id}")
  suspend fun deleteMood(@Path("id") id: String): Response<Boolean>
}