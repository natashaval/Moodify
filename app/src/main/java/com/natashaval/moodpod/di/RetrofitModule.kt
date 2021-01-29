package com.natashaval.moodpod.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.natashaval.moodpod.di.url.AffirmationUrl
import com.natashaval.moodpod.di.url.MoodPodUrl
import com.natashaval.moodpod.di.url.QuoteUrl
import com.natashaval.moodpod.utils.UrlConstants.AFFIRMATION_URL
import com.natashaval.moodpod.utils.UrlConstants.MOODPOD_URL
import com.natashaval.moodpod.utils.UrlConstants.QUOTE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by natasha.santoso on 14/01/21.
 */
@Module
@InstallIn(ApplicationComponent::class)
class RetrofitModule {

  @Provides
  @Singleton
  @MoodPodUrl
  fun provideMoodPodRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder().baseUrl(MOODPOD_URL).client(okHttpClient).addConverterFactory(
      GsonConverterFactory.create(gson)).build()
  }

  @Provides
  @Singleton
  @AffirmationUrl
  fun provideAffirmationRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder().baseUrl(AFFIRMATION_URL).client(okHttpClient).addConverterFactory(
        GsonConverterFactory.create(gson)).build()
  }

  @Provides
  @Singleton
  @QuoteUrl
  fun provideZenRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder().baseUrl(QUOTE_URL).addConverterFactory(
        GsonConverterFactory.create(gson)).build()
  }

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
        .addInterceptor { chain -> onIntercept(chain) }
        .build()
  }

  @Provides
  @Singleton
  fun provideGson(): Gson {
    return GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create()
  }

  // https://stackoverflow.com/questions/29921667/retrofit-2-catch-connection-timeout-exception
  @Throws(IOException::class)
  private fun onIntercept(chain: Interceptor.Chain): Response {
    try {
      val response: Response = chain.proceed(chain.request())
      return response.newBuilder().body(response.body).build()
    } catch (exception: SocketTimeoutException) {
      exception.printStackTrace()
    }
    return chain.proceed(chain.request())
  }

}