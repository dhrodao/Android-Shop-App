package com.dhrodao.androidshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhrodao.androidshop.items.ItemTypes
import com.dhrodao.androidshop.util.BasketItem

class ShopViewModel(val itemType: ItemTypes, val basketItems: LiveData<ArrayList<BasketItem>>, val globalBasketPrice: MutableLiveData<Double>, private val allBasketItems: LiveData<ArrayList<BasketItem>>) : ViewModel() {
    private val _itemPrice = MutableLiveData(0.00)
    val itemPrice: LiveData<Double>
        get() = _itemPrice

    private val _computedItemsPrice = MutableLiveData(0.00)
    val computedItemsPrice: LiveData<Double>
        get() = _computedItemsPrice

    private val _itemsQuantity = MutableLiveData(0)
    val itemsQuantity: LiveData<Int>
        get() = _itemsQuantity

    private val _basketPrice = MutableLiveData(0.00)
    val basketPrice: LiveData<Double>
        get() = _basketPrice

    private val _currentSpinnerItem = MutableLiveData(0)
    val currentSpinnerItem: LiveData<Int>
        get() = _currentSpinnerItem

    private val _itemSelected = MutableLiveData<BasketItem>()
    val itemSelected: LiveData<BasketItem>
        get() = _itemSelected

    fun updatePrice() {
        _computedItemsPrice.value = _itemPrice.value!! * _itemsQuantity.value!!
    }

    fun setPrice(price: Double) {
        _itemPrice.value = price
        updatePrice()
    }

    fun updateBasketPrice() {
        val addedPrice = itemsQuantity.value?.toDouble()!! * itemPrice.value!!
        _basketPrice.value = _basketPrice.value!! + addedPrice
        globalBasketPrice.value = globalBasketPrice.value?.plus(addedPrice)
    }

    fun setBasketPrice(price: Double) {
        _basketPrice.value = price
    }

    fun setGlobalBasketPrice(price: Double) {
        globalBasketPrice.value = price
    }

    fun updateItemsQuantity(value: Int) {
        _itemsQuantity.value = value
    }

    fun resetItems() {
        _itemsQuantity.value = 0
        _computedItemsPrice.value = 0.00
    }

    fun getBasketSize() : Int {
        return allBasketItems.value!!.size
    }

    fun addToBasket(item: BasketItem) {
        allBasketItems.value!!.add(item)
        basketItems.value!!.add(item)
    }

    fun setCurrentSpinnerItem(position: Int) {
        _currentSpinnerItem.value = position
    }

    //fun getCurrentSpinnerItem() : Int {
    //    return _currentFruitSpinnerItem.value!!
    //}

    fun setSelectedItem(item: BasketItem) {
        _itemSelected.value = item
    }

    fun getSelectedItem() : BasketItem {
        return _itemSelected.value!!
    }

    fun getBasketItems() : ArrayList<BasketItem> {
        return basketItems.value!!
    }

    fun getGlobalBasketItems() : ArrayList<BasketItem> {
        return allBasketItems.value!!
    }
}