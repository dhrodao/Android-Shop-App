package com.dhrodao.androidshop.util

import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import android.widget.TextView
import com.dhrodao.androidshop.items.FruitItems
import com.dhrodao.androidshop.fruitshop.viewmodel.FruitShopViewModel
import com.dhrodao.androidshop.main.R
import com.dhrodao.androidshop.main.databinding.FragmentFruitShopBinding

class CustomSpinnerSelectorListener(private val fruitShopBinding: FragmentFruitShopBinding,
                                    private val fruits : Array<FruitItems>,
                                    private val fruitShopViewModel: FruitShopViewModel,
                                    private val seekBar: SeekBar
) : AdapterView.OnItemSelectedListener {
    private var fruitText : String = ""
    private var prevPosition : Int? = null
    var currentFruitItem : FruitItems? = null

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        resetData(position)

        fruitText = parent?.findViewById<TextView>(R.id.text).let { it?.text }.toString()
        currentFruitItem = getFruitItem()

        if (position == 0) {
            setControlElementsVisibility(View.INVISIBLE)
            return
        } else setControlElementsVisibility(View.VISIBLE)

        fruitShopViewModel.setCurrentSpinnerItem(position)
        updateFruitPrice(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun resetData(position: Int) {
        val fruitQuantity = fruitShopViewModel.fruitQuantity.value!!
        fruitShopViewModel.updateFruitQuantity(fruitQuantity)
        seekBar.progress = fruitQuantity

        prevPosition = position
    }

    private fun setControlElementsVisibility(visibility : Int) {
        fruitShopBinding.apply {
            quantityLayout.visibility = visibility
            priceLayout.visibility = visibility
            addBasketButton.visibility = visibility
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