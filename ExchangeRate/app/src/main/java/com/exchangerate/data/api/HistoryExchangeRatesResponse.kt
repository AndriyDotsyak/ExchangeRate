package com.exchangerate.data.api

import com.exchangerate.data.model.HistoryRate
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class HistoryExchangeRatesResponse(
    @SerializedName("rates") val rates: JsonObject,
    @SerializedName("base") val base: String,
    @SerializedName("start_at") val startAt: String,
    @SerializedName("end_at") val endAt: String,
    var symbols: String
) {

    fun getRate(date: String): HistoryRate? = if (!JSONObject(rates.toString()).isNull(date))
        HistoryRate(date, rates[date].asJsonObject[symbols].asDouble) else null
}