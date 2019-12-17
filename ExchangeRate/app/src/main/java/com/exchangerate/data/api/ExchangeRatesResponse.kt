package com.exchangerate.data.api

import com.exchangerate.data.model.Rate
import com.google.gson.annotations.SerializedName

data class ExchangeRatesResponse(
    @SerializedName("rates") val rates: Rates,
    @SerializedName("base") val base: String,
    @SerializedName("date") val date: String
)

data class Rates(
    @SerializedName("CAD") val cad: Double,
    @SerializedName("HKD") val hkd: Double,
    @SerializedName("ISK") val isk: Double,
    @SerializedName("PHP") val php: Double,
    @SerializedName("DKK") val dkk: Double,
    @SerializedName("HUF") val huf: Double,
    @SerializedName("CZK") val czk: Double,
    @SerializedName("GBP") val gbp: Double,
    @SerializedName("RON") val ron: Double,
    @SerializedName("SEK") val sek: Double,
    @SerializedName("IDR") val idr: Double,
    @SerializedName("INR") val inr: Double,
    @SerializedName("BRL") val brl: Double,
    @SerializedName("RUB") val rub: Double,
    @SerializedName("HRK") val hrk: Double,
    @SerializedName("JPY") val jpy: Double,
    @SerializedName("THB") val thb: Double,
    @SerializedName("CHF") val chf: Double,
    @SerializedName("EUR") val eur: Double,
    @SerializedName("MYR") val myr: Double,
    @SerializedName("BGN") val bgn: Double,
    @SerializedName("TRY") val TRY: Double,
    @SerializedName("CNY") val cny: Double,
    @SerializedName("NOK") val nok: Double,
    @SerializedName("NZD") val nzd: Double,
    @SerializedName("ZAR") val zar: Double,
    @SerializedName("USD") val usd: Double,
    @SerializedName("MXN") val mxn: Double,
    @SerializedName("SGD") val sgd: Double,
    @SerializedName("AUD") val aud: Double,
    @SerializedName("ILS") val ils: Double,
    @SerializedName("KRW") val krw: Double,
    @SerializedName("PLN") val pln: Double
) {

    fun getList(): List<Rate> = listOf(
        Rate("CAD", cad),
        Rate("HKD", hkd),
        Rate("ISK", isk),
        Rate("PHP", php),
        Rate("DKK", dkk),
        Rate("HUF", huf),
        Rate("CZK", czk),
        Rate("GBP", gbp),
        Rate("RON", ron),
        Rate("SEK", sek),
        Rate("IDR", idr),
        Rate("INR", inr),
        Rate("BRL", brl),
        Rate("RUB", rub),
        Rate("HRK", hrk),
        Rate("JPY", jpy),
        Rate("THB", thb),
        Rate("CHF", chf),
        Rate("EUR", eur),
        Rate("MYR", myr),
        Rate("BGN", bgn),
        Rate("TRY", TRY),
        Rate("CNY", cny),
        Rate("NOK", nok),
        Rate("NZD", nzd),
        Rate("ZAR", zar),
        Rate("USD", usd),
        Rate("MXN", mxn),
        Rate("SGD", sgd),
        Rate("AUD", aud),
        Rate("ILS", ils),
        Rate("KRW", krw),
        Rate("PLN", pln)
    )
}