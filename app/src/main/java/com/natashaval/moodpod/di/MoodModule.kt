package com.natashaval.moodpod.di

import com.natashaval.moodpod.api.MoodApi
import com.natashaval.moodpod.di.url.MoodPodUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

/**
 * Created by natasha.santoso on 29/01/21.
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
class MoodModule {
  @Provides
  fun provideMoodApi(@MoodPodUrl retrofit: Retrofit): MoodApi {
    return retrofit.create(MoodApi::class.java)
  }
}