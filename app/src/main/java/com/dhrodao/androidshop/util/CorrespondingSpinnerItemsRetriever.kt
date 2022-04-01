package com.dhrodao.androidshop.util

import com.dhrodao.androidshop.items.BasketItems
import com.dhrodao.androidshop.items.ItemTypes

object CorrespondingSpinnerItemsRetriever {
    fun getSpinnerItems(itemType: ItemTypes) : ArrayList<BasketItems> {
        val itemArrayList = ArrayList<BasketItems>()
        BasketItems.values().forEach {
            if (it.type == itemType)
                itemArrayList.add(it)
        }
        return itemArrayList
    }
}