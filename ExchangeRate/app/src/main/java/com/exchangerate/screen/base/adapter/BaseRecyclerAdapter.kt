package com.exchangerate.screen.base.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<I: Any, VH: BaseViewHolder<I>>(
    val onItemClickListener: OnItemClickListener<I>) : RecyclerView.Adapter<VH>() {

    val items = mutableListOf<I>()

    interface OnItemClickListener<I> {
        fun onItemClick(position: Int, item: I)
    }

    override fun getItemCount() = items.size

    fun addItems(items: List<I>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItem(item: I) {
        items.add(item)
        notifyItemChanged(items.size - 1)
    }

    fun removeItem(item: I) {
        items.remove(item)
        notifyDataSetChanged()
    }

}

abstract class BaseViewHolder<T: Any>(itemView: View,
    onItemClickListener: BaseRecyclerAdapter.OnItemClickListener<T>
) : RecyclerView.ViewHolder(itemView) {

    lateinit var item: T

    init {
        itemView.setOnClickListener {
            onItemClickListener.onItemClick(adapterPosition, item)
        }
    }

    fun onBind(item: T) {
        this.item = item
        initUI()
    }

    abstract fun initUI()
}