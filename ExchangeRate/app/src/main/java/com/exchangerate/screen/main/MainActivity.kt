package com.exchangerate.screen.main

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.exchangerate.R
import com.exchangerate.data.api.ExchangeRatesResponse
import com.exchangerate.data.api.HistoryExchangeRatesResponse
import com.exchangerate.data.model.ExchangeRate
import com.exchangerate.data.model.HistoryRate
import com.exchangerate.data.model.Rate
import com.exchangerate.screen.base.BaseActivity
import com.exchangerate.screen.base.adapter.BaseRecyclerAdapter
import com.exchangerate.screen.main.adapter.СurrencyAdapter
import com.exchangerate.screen.schedule.ScheduleActivity
import io.realm.RealmList
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var currencyAdapter: СurrencyAdapter

    val daysWeek = 7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this@MainActivity, viewModelFactory).get(MainViewModel::class.java)
        mainViewModel.progressLive.observe(this@MainActivity, Observer { handleProgress(it) })
        mainViewModel.errorLive.observe(this@MainActivity, Observer { handleError(it) })
        mainViewModel.exchangeRatesLive.observe(this@MainActivity, Observer { handleExchangeRates(it) })
        mainViewModel.historyExchangeRatesLive.observe(this@MainActivity, Observer { handleHistoryExchangeRates(it) })

        setupRecycler()
        updateExchangeRate()
    }

    private fun updateExchangeRate() {
        if (isUpdateDate()) {
            if (isUpdateTime()) {
                val ratesList: MutableList<Rate> = mutableListOf()
                mainViewModel.getItemDB().rates?.let { ratesList.addAll(it) }

                currencyAdapter.addItems(ratesList)
            } else {
                mainViewModel.getExchangeRates()
            }
        } else {
            mainViewModel.getExchangeRates()
        }
    }

    private fun setupRecycler() {
        val layoutManager = LinearLayoutManager(applicationContext)
        val decoration = DividerItemDecoration(recycler_view_AM.context, LinearLayoutManager.VERTICAL)
        currencyAdapter = СurrencyAdapter(onItemClickListener)

        recycler_view_AM.layoutManager = layoutManager
        recycler_view_AM.adapter = currencyAdapter
        recycler_view_AM.addItemDecoration(decoration)
    }

    private val onItemClickListener = object : BaseRecyclerAdapter.OnItemClickListener<Rate> {
        override fun onItemClick(position: Int, item: Rate) {
            mainViewModel.getHistoryExchangeRates(getDateCalculator(-daysWeek), getCurrentDate(), item.currency)
        }
    }

    fun handleError(throwable: Throwable) {
        showErrorDialog(resources.getString(R.string.am_error_unknown_error))
    }

    private fun handleExchangeRates(exchangeRatesResponse: ExchangeRatesResponse) {

        val rates: RealmList<Rate> = RealmList()
        rates.addAll(exchangeRatesResponse.rates.getList())

        val exchangeRate = ExchangeRate(
            rates = rates,
            base = exchangeRatesResponse.base,
            date = getCurrentDate(),
            time = getCurrentTime()
        )

        currencyAdapter.addItems(exchangeRatesResponse.rates.getList())
        mainViewModel.addItemDB(exchangeRate)
    }

    private fun handleHistoryExchangeRates(historyExchangeRatesResponse: HistoryExchangeRatesResponse) {
        val historyRates: MutableList<HistoryRate> = mutableListOf()

        for (item in -daysWeek..0) {
            historyExchangeRatesResponse.getRate(getDateCalculator(item))?.let { historyRates.add(it) }
        }

        if (historyRates.isNotEmpty()) {
            ScheduleActivity.start(this@MainActivity, historyRates as ArrayList<HistoryRate>)
        } else {
            showErrorDialog(resources.getString(R.string.am_error_no_exchange_rate))
        }
    }

    private fun isUpdateDate(): Boolean = mainViewModel.getItemDB().date == getCurrentDate()

    private fun isUpdateTime(): Boolean {
        val minutesPerHour = 60
        val minuteUpdate = 10

        val minutePreviousTime: Int = mainViewModel.getItemDB().time?.let { it.hour * minutesPerHour + it.minute } ?: 0
        val minuteCurrentTime = getCurrentTime().hour * minutesPerHour + getCurrentTime().minute

        return minuteCurrentTime - minutePreviousTime < minuteUpdate
    }
}