package com.dhrodao.androidshop.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhrodao.androidshop.entities.Order
import com.dhrodao.androidshop.main.R

class CustomOrderRecyclerAdapter : RecyclerView.Adapter<CustomOrderRecyclerAdapter.DataViewHolder>() {
    private val dataSet : ArrayList<Order> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder {
        val basketView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return DataViewHolder(basketView)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.setData(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class DataViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val orderName : TextView = itemView.findViewById(R.id.order_name)
        private val items : TextView = itemView.findViewById(R.id.item_list)

        fun setData(order: Order) {
            val orderNameText = "Pedido ${order.id} - Precio: ${order.totalPrice}€ - Fecha: ${order.orderDate}"
            orderName.text = orderNameText

            order.orderedItems.forEach {
                val newItem = "${it.item} x${it.quantity}\n"
                items.append(newItem)
            }
        }
    }

    fun clearItems() {
        dataSet.clear()
    }

    fun addItem(item : Order) {
        (dataSet as ArrayList).add(item)

        notifyItemInserted(dataSet.size - 1)
    }

    fun isItemInList(item : Order) : Boolean {
        return dataSet.contains(item)
    }
}