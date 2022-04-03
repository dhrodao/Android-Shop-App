package com.dhrodao.androidshop.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhrodao.androidshop.main.R

class CustomShopRecyclerAdapter(private val dataSet : ArrayList<BasketItem>) : RecyclerView.Adapter<CustomShopRecyclerAdapter.DataViewHolder>(), View.OnClickListener {
    private var listener: View.OnClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder {
        val basketView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_fruit_basket, parent, false)

        basketView.setOnClickListener(this)

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
            textView.text = basketItem.item

            val auxText = "x${basketItem.quantity}"
            fruitQuantityText.text = auxText
        }
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        this.listener = listener
    }

    override fun onClick(view: View?) {
        this.listener?.onClick(view)
    }
}