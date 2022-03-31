package com.dhrodao.androidshop.fruitshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhrodao.androidshop.util.BasketItem

class FruitShopViewModel() : ViewModel() {
    private val _fruitPrice = MutableLiveData(0.00)
    val fruitPrice: LiveData<Double>
        get() = _fruitPrice

    private val _computedFruitPrice = MutableLiveData(0.00)
    val computedFruitPrice: LiveData<Double>
        get() = _computedFruitPrice

    private val _fruitQuantity = MutableLiveData(0)
    val fruitQuantity: LiveData<Int>
        get() = _fruitQuantity

    private val _basketPrice = MutableLiveData(0.00)
    val basketPrice: LiveData<Double>
        get() = _basketPrice

    private val _basketItems = MutableLiveData<ArrayList<BasketItem>>(ArrayList())
    val basketItems: LiveData<ArrayList<BasketItem>>
        get() = _basketItems

    private val _currentSpinnerItem = MutableLiveData<Int>(0)
    val currentSpinnerItem: LiveData<Int>
        get() = _currentSpinnerItem

    private val _fruitItemSelected = MutableLiveData<BasketItem>()
    val fruitItemSelected: LiveData<BasketItem>
        get() = _fruitItemSelected

    fun updateFruitPrice() {
        _computedFruitPrice.value = _fruitPrice.value!! * _fruitQuantity.value!!
    }

    fun updateFruitPrice(price: Double) {
        _fruitPrice.value = price
        updateFruitPrice()
    }

    fun updateBasketPrice() {
        _basketPrice.value = _basketPrice.value!! +
                fruitQuantity.value?.toDouble()!! * fruitPrice.value!!
    }

    fun updateFruitQuantity(value: Int) {
        _fruitQuantity.value = value
    }

    fun resetFruit() {
        _fruitQuantity.value = 0
        _computedFruitPrice.value = 0.00
    }

    fun getBasketSize() : Int {
        return basketItems.value!!.size
    }

    fun addToBasket(item: BasketItem) {
        basketItems.value!!.add(item)
    }

    fun setCurrentSpinnerItem(position: Int) {
        _currentSpinnerItem.value = position
    }

    fun getCurrentSpinnerItem() : Int {
        return _currentSpinnerItem.value!!
    }

    fun setFruitItemSelected(item: BasketItem) {
        _fruitItemSelected.value = item
    }

    fun getFruitItemSelected() : BasketItem {
        return _fruitItemSelected.value!!
    }
}