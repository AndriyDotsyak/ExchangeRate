package com.exchangerate.screen.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.exchangerate.R
import com.exchangerate.data.model.Rate
import com.exchangerate.screen.base.adapter.BaseRecyclerAdapter
import com.exchangerate.screen.base.adapter.BaseViewHolder

class СurrencyAdapter(
    onItemClickListener: OnItemClickListener<Rate>
) : BaseRecyclerAdapter<Rate, CurrencyViewHolder>(onItemClickListener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
        return CurrencyViewHolder(view, onItemClickListener)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.onBind(items[position])
    }
}

class CurrencyViewHolder(itemView: View,
    onItemClickListener: BaseRecyclerAdapter.OnItemClickListener<Rate>
) : BaseViewHolder<Rate>(itemView, onItemClickListener) {

    private val textViewCurrency: TextView = itemView.findViewById(R.id.text_view_currency_IC)
    private val textViewRate: TextView = itemView.findViewById(R.id.text_view_rate_IC)

    override fun initUI() {
        textViewCurrency.text = item.currency
        textViewRate.text = String.format("%.2f", item.rate)
    }
}