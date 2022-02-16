package com.dhrodao.androidfruitselector

import android.widget.TextView

class FruitShopManager(private val priceValueTextView: TextView,
                       private val progressValueTextView: TextView,
                       private val totalValueTextView: TextView
) {
    fun updateFruitPriceText(computedFruitPrice : Double) {
        val priceText = "Precio: %.2f €".format(computedFruitPrice)
        priceValueTextView.text = priceText
    }

    fun updateFruitQuantityText(fruitQuantity : Int) {
        progressValueTextView.text = fruitQuantity.toString()
    }

    fun updateBasketPriceText(basketPrice : Double) {
        totalValueTextView.also { textView ->
            val basketPriceText = "Total: %.2f €".format(basketPrice)
            textView.text = basketPriceText
        }
    }
}