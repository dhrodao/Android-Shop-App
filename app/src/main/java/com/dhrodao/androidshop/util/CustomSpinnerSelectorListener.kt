package com.dhrodao.androidshop.util

import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import android.widget.TextView
import com.dhrodao.androidshop.items.FruitItems
import com.dhrodao.androidshop.fruitshop.viewmodel.FruitShopViewModel
import com.dhrodao.androidshop.main.R

class CustomSpinnerSelectorListener(private val viewsAffected : Array<View>,
                                    private val fruits : Array<FruitItems>,
                                    private val fruitShopViewModel: FruitShopViewModel,
                                    private val seekBar: SeekBar
) : AdapterView.OnItemSelectedListener {
    private var fruitText : String = ""
    private var prevPosition : Int? = null
    var currentFruitItem : FruitItems? = null
    var onRestore : Boolean = false

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        resetData(position)

        fruitText = parent?.findViewById<TextView>(R.id.text).let { it?.text }.toString()
        currentFruitItem = getFruitItem()

        if (position == 0) {
            setVisibility(viewsAffected, View.INVISIBLE)
            return
        }
        setVisibility(viewsAffected, View.VISIBLE)

        updateFruitPrice(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun resetData(position: Int) {
        prevPosition?.let { if (it != position) onRestore = false }

        if (!onRestore) {
            fruitShopViewModel.updateFruitQuantity(0)
            seekBar.progress = 0
        }

        prevPosition = position
    }

    private fun setVisibility(viewsAffected: Array<View>, visibility : Int) {
        for (view in viewsAffected) {
            view.visibility = visibility
        }
    }

    private fun getFruitPrice(position: Int) : Double {
        return fruits[position - 1].price
    }

    private fun updateFruitPrice(position: Int) {
        fruitShopViewModel.updateFruitPrice(getFruitPrice(position))
    }

    private fun getFruitItem() : FruitItems? {
        for (f in fruits){
            if (f.fruit.lowercase() == fruitText.lowercase()){
                return f
            }
        }

        return null
    }
}