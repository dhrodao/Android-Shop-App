package com.dhrodao.androidshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dhrodao.androidshop.items.ItemTypes
import com.dhrodao.androidshop.util.BasketItem

class MainViewModel : ViewModel() {
    private var username: MutableLiveData<String> = MutableLiveData("")

    private val _fruitBasketItems = MutableLiveData<ArrayList<BasketItem>>(ArrayList())
    private val fruitBasketItems: LiveData<ArrayList<BasketItem>>
        get() = _fruitBasketItems

    private val _sportBasketItems = MutableLiveData<ArrayList<BasketItem>>(ArrayList())
    private val sportBasketItems: LiveData<ArrayList<BasketItem>>
        get() = _sportBasketItems

    private val _butcherBasketItems = MutableLiveData<ArrayList<BasketItem>>(ArrayList())
    private val butcherBasketItems: LiveData<ArrayList<BasketItem>>
        get() = _butcherBasketItems

    private val _fishBasketItems = MutableLiveData<ArrayList<BasketItem>>(ArrayList())
    private val fishBasketItems: LiveData<ArrayList<BasketItem>>
        get() = _fishBasketItems

    private val _basketItems = MutableLiveData<ArrayList<BasketItem>>(ArrayList())
    val basketItems: LiveData<ArrayList<BasketItem>>
        get() = _basketItems

    private val _basketPrice = MutableLiveData(0.0)
    val basketPrice: LiveData<Double>
        get() = _basketPrice

    val fruitShopViewModel: ShopViewModel = ShopViewModel(ItemTypes.FRUIT, fruitBasketItems, _basketPrice, basketItems)
    val sportsShopViewModel: ShopViewModel = ShopViewModel(ItemTypes.SPORT, sportBasketItems, _basketPrice, basketItems)
    val butcherShopViewModel: ShopViewModel = ShopViewModel(ItemTypes.BUTCHER, butcherBasketItems, _basketPrice, basketItems)
    val fishShopViewModel: ShopViewModel = ShopViewModel(ItemTypes.FISH, fishBasketItems, _basketPrice, basketItems)

    /* USER */

    fun setUserName(name: String) {
        username.value = name
    }

    fun getUserName(): String {
        return username.value.toString()
    }
}