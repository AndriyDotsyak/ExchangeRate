package com.exchangerate.screen.main

import androidx.lifecycle.MutableLiveData
import com.exchangerate.data.api.ExchangeRateApi
import com.exchangerate.data.api.ExchangeRatesResponse
import com.exchangerate.data.api.HistoryExchangeRatesResponse
import com.exchangerate.data.model.ExchangeRate
import com.exchangerate.screen.base.BaseViewModel
import io.realm.Realm
import javax.inject.Inject

class MainViewModel @Inject constructor(private val exchangeRateApi: ExchangeRateApi) : BaseViewModel() {

    val exchangeRatesLive = MutableLiveData<ExchangeRatesResponse>()
    val historyExchangeRatesLive = MutableLiveData<HistoryExchangeRatesResponse>()

    fun getExchangeRates() {
        launchDataLoadWithProgress {
            val response = exchangeRateApi.getExchangeRates().await()

            if (response.isSuccessful) {
                val exchangeRatesResponses = response.body()!!
                exchangeRatesLive.value = exchangeRatesResponses
            } else {
                // TODO Api Exception
                //throw ApiException(message())
            }
        }
    }

    fun getHistoryExchangeRates(startAt: String, endAt: String, symbols: String) {
        launchDataLoadWithProgress {
            val response = exchangeRateApi
                .getHistoryExchangeRates(startAt = startAt, endAt = endAt, symbols = symbols)
                .await()

            if (response.isSuccessful) {
                val historyExchangeRatesResponse = response.body()!!
                historyExchangeRatesResponse.symbols = symbols
                historyExchangeRatesLive.value = historyExchangeRatesResponse
            } else {
                // TODO Api Exception
            }
        }
    }

    fun addItemDB(exchangeRate: ExchangeRate) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction { transaction ->
                transaction.where(ExchangeRate::class.java).findAll().deleteAllFromRealm()
                transaction.insert(exchangeRate)
            }
        }
    }

    fun getItemDB() : ExchangeRate {
        var exchangeRate = ExchangeRate()

        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction { transaction ->
                transaction.where(ExchangeRate::class.java).findFirst()?.let {
                    exchangeRate = it
                }
            }
        }

        return exchangeRate
    }
}