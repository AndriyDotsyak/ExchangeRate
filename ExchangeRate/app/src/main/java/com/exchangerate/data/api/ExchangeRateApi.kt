package com.exchangerate.data.api

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeRateApi {

    @GET("latest")
    fun getExchangeRates(
        @Query("base") base: String = "USD"
    ): Deferred<Response<ExchangeRatesResponse>>

    @GET("history")
    fun getHistoryExchangeRates(
        @Query("start_at") startAt: String,
        @Query("end_at") endAt: String,
        @Query("base") base: String = "USD",
        @Query("symbols") symbols: String
    ): Deferred<Response<HistoryExchangeRatesResponse>>
}