package com.exchangerate.screen.schedule

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.exchangerate.R
import com.exchangerate.data.model.HistoryRate
import com.exchangerate.screen.base.BaseActivity
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_schedule.*
import kotlin.collections.ArrayList

class ScheduleActivity : BaseActivity() {

    companion object {
        const val HISTORY_RATES_KEY = "HISTORY_RATES_KEY"

        fun start(context: Context, historyRates: ArrayList<HistoryRate>) {
            val intent = Intent(context, ScheduleActivity::class.java)
            intent.putParcelableArrayListExtra(HISTORY_RATES_KEY, historyRates)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        val historyRates: List<HistoryRate> = intent.getParcelableArrayListExtra(HISTORY_RATES_KEY) ?: listOf()

        setupGraph(historyRates)
    }

    private fun setupGraph(historyRates: List<HistoryRate>) {
        val lineGraphSeries: LineGraphSeries<DataPoint> = LineGraphSeries(getDataPoint(historyRates))

        graph_view_AS.addSeries(lineGraphSeries)
        graph_view_AS.gridLabelRenderer.labelFormatter = object : DefaultLabelFormatter() {
            override fun formatLabel(value: Double, isValueX: Boolean): String {

                if (isValueX) return getSimpleDateFormat(DAY_FORMAT_PATTERN, value)
                return super.formatLabel(value, isValueX)
            }
        }
    }

    private fun getDataPoint(historyRates: List<HistoryRate>): Array<DataPoint> {
        val dataPoint: Array<DataPoint> = Array(historyRates.count()) { DataPoint(0.0, 0.0) }

        for (item in 0 until historyRates.count()) {
            dataPoint[item] = DataPoint(
                getSimpleDateParse(DATE_FORMAT_PATTERN, historyRates[item].date),
                historyRates[item].rate
            )
        }

        return dataPoint
    }
}