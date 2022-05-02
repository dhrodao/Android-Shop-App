package com.dhrodao.androidshop.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhrodao.androidshop.dao.ItemDao
import com.dhrodao.androidshop.entities.Item
import com.dhrodao.androidshop.items.ItemTypes
import com.dhrodao.androidshop.util.BasketItem
import kotlinx.coroutines.launch

class MainViewModel(val itemDao: ItemDao) : ViewModel() {
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

    val items = itemDao.getItems()
    val fruitItems = itemDao.getItemsByType(ItemTypes.FRUIT)
    val fishItems = itemDao.getItemsByType(ItemTypes.FISH)
    val butcherItems = itemDao.getItemsByType(ItemTypes.BUTCHER)
    val sportItems = itemDao.getItemsByType(ItemTypes.SPORT)

    /* USER */

    fun setUserName(name: String) {
        username.value = name
    }

    fun getUserName(): String {
        return username.value.toString()
    }

    fun clearItems(){
        viewModelScope.launch {
            itemDao.clear()
        }
    }

    fun insertItem(item: Item) {
        viewModelScope.launch {
            itemDao.insertItem(item)
        }
    }

    /*init {
        clearItems()
        insertItem(Item(0, ItemTypes.FRUIT, "Piña", 1.5, R.drawable.pineapple))
        insertItem(Item(0, ItemTypes.FRUIT, "Uva", 0.75, R.drawable.uva))
        insertItem(Item(0, ItemTypes.FRUIT, "Pera", 0.55, R.drawable.pear))
        insertItem(Item(0, ItemTypes.FRUIT, "Naranja", 0.60, R.drawable.orange))

        insertItem(Item(0, ItemTypes.SPORT, "Chandal", 49.99, R.drawable.chandal))
        insertItem(Item(0, ItemTypes.SPORT, "Toalla", 19.99, R.drawable.toalla))
        insertItem(Item(0, ItemTypes.SPORT, "Zapatillas", 99.99, R.drawable.zapatillas))

        insertItem(Item(0, ItemTypes.BUTCHER, "Ternera", 2.75, R.drawable.ternera))
        insertItem(Item(0, ItemTypes.BUTCHER, "Pollo", 2.50, R.drawable.pollo))
        insertItem(Item(0, ItemTypes.BUTCHER, "Gallina", 2.99, R.drawable.gallina))

        insertItem(Item(0, ItemTypes.FISH, "Trucha", 3.3, R.drawable.trucha))
        insertItem(Item(0, ItemTypes.FISH, "Salmón", 5.0, R.drawable.salmon))
        insertItem(Item(0, ItemTypes.FISH, "Atún", 2.5, R.drawable.atun))
    }*/
}