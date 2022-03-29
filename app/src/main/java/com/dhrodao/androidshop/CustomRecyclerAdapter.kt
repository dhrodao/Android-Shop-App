package com.dhrodao.androidshop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerAdapter(private val dataSet : ArrayList<BasketItem>) : RecyclerView.Adapter<CustomRecyclerAdapter.DataViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder {
        val basketView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_fruit_basket, parent, false)
        return DataViewHolder(basketView)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.setData(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class DataViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val fruitImage = itemView.findViewById<ImageView>(R.id.image)
        private val textView = itemView.findViewById<TextView>(R.id.text)
        private val fruitQuantityText = itemView.findViewById<TextView>(R.id.fruit_quantity)

        fun setData(basketItem: BasketItem) {
            fruitImage.setImageResource(basketItem.icon)
            textView.text = basketItem.fruit

            val auxText = "x${basketItem.quantity}"
            fruitQuantityText.text = auxText
        }
    }
}