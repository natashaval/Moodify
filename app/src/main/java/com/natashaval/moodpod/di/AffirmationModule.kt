package com.natashaval.moodpod.di

import com.natashaval.moodpod.api.AffirmationApi
import com.natashaval.moodpod.api.QuoteApi
import com.natashaval.moodpod.di.url.AffirmationUrl
import com.natashaval.moodpod.di.url.QuoteUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import retrofit2.Retrofit

/**
 * Created by natasha.santoso on 14/01/21.
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
class AffirmationModule {
  @Provides
  fun provideAffirmationApi(@AffirmationUrl retrofit: Retrofit) : AffirmationApi {
    return retrofit.create(AffirmationApi::class.java)
  }

  @Provides
  fun provideQuoteApi(@QuoteUrl retrofit: Retrofit): QuoteApi {
    return retrofit.create(QuoteApi::class.java)
  }
}