package com.exchangerate.di.module

import com.exchangerate.BuildConfig
import com.exchangerate.data.api.ExchangeRateApi
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    fun provideExchangeRatesApi(httpClient: OkHttpClient) = buildRetrofit(BuildConfig.ENDPOINT, httpClient)
        .create(ExchangeRateApi::class.java)

    fun buildRetrofit(url: String, httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(httpClient)
            .addConverterFactory(createGsonConverterFactory())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    fun createGsonConverterFactory() = GsonConverterFactory.create(GsonBuilder().create())

    @Provides
    @Singleton
    fun provideOkHttpClient(headerInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .readTimeout(30L, TimeUnit.SECONDS)
            .connectTimeout(30L, TimeUnit.SECONDS)
            .pingInterval(30, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .addInterceptor(headerInterceptor)
            .build()

    @Provides
    fun provideHeaderInterceptor() = Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Content-Type", "application/json")
        chain.proceed(requestBuilder.build())
    }
}