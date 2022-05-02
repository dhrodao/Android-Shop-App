package com.dhrodao.androidshop.util

import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import android.widget.TextView
import com.dhrodao.androidshop.entities.Item
import com.dhrodao.androidshop.items.BasketItems
import com.dhrodao.androidshop.main.R
import com.dhrodao.androidshop.main.databinding.FragmentFruitShopBinding
import com.dhrodao.androidshop.viewmodel.ShopViewModel

class CustomSpinnerSelectorListener(private val affectedInterfaceItems: Array<View>,
                                    private val baskets : List<Item>,
                                    private val fruitShopViewModel: ShopViewModel,
                                    private val seekBar: SeekBar
) : AdapterView.OnItemSelectedListener {
    private var fruitText : String = ""
    private var prevPosition : Int? = null
    var currentBasketItem : Item? = null

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        resetData(position)

        fruitText = parent?.findViewById<TextView>(R.id.text).let { it?.text }.toString()
        currentBasketItem = getFruitItem()

        if (position == 0) {
            setControlElementsVisibility(View.INVISIBLE)
            return
        } else setControlElementsVisibility(View.VISIBLE)

        fruitShopViewModel.setCurrentSpinnerItem(position)
        updateFruitPrice(position)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun resetData(position: Int) {
        val fruitQuantity = fruitShopViewModel.itemsQuantity.value!!
        fruitShopViewModel.updateItemsQuantity(fruitQuantity)
        seekBar.progress = fruitQuantity

        prevPosition = position
    }

    private fun setControlElementsVisibility(visibility : Int) {
        for(item in affectedInterfaceItems) item.visibility = visibility
    }

    private fun getFruitPrice(position: Int) : Double {
        return baskets[position - 1].price
    }

    private fun updateFruitPrice(position: Int) {
        fruitShopViewModel.setPrice(getFruitPrice(position))
    }

    private fun getFruitItem() : Item? {
        for (f in baskets){
            if (f.name.lowercase() == fruitText.lowercase()){
                return f
            }
        }

        return null
    }
}