package com.dhrodao.androidshop.viewmodel

import android.app.Application
import android.icu.text.DateFormat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.dhrodao.androidshop.dao.ItemDao
import com.dhrodao.androidshop.dao.OrderDao
import com.dhrodao.androidshop.entities.Item
import com.dhrodao.androidshop.entities.Order
import com.dhrodao.androidshop.items.ItemTypes
import com.dhrodao.androidshop.main.R
import com.dhrodao.androidshop.util.BasketItem
import com.dhrodao.androidshop.worker.ItemWorker
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class MainViewModel(val application: Application, val itemDao: ItemDao, val orderDao: OrderDao) : ViewModel() {
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

    val fruitItems = itemDao.getItemsByType(ItemTypes.FRUIT)
    val fishItems = itemDao.getItemsByType(ItemTypes.FISH)
    val butcherItems = itemDao.getItemsByType(ItemTypes.BUTCHER)
    val sportItems = itemDao.getItemsByType(ItemTypes.SPORT)

    val items = itemDao.getItems()
    var orders = orderDao.getOrdersByUsername("dhrodao")

    private val workManager = WorkManager.getInstance(application)

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

    fun purchaseItems() {
        viewModelScope.launch {
            basketItems.value?.let { items ->
                basketPrice.value?.let { price ->
                Order(0, username.value.toString(),
                    price, items,
                    DateFormat.getDateInstance(
                        DateFormat.FULL,
                        Locale("es", "ES")
                    ).format(Date()))
            } }
                ?.let {
                    orderDao.insertOrder(it)
                    Log.d("MainViewModel", "Order inserted")
                }
        }
    }

    init {
        // Init database
        clearItems()
        insertItem(Item(0, ItemTypes.FRUIT, "Piña", 1.5, R.drawable.pineapple))
        insertItem(Item(1, ItemTypes.FRUIT, "Uva", 0.75, R.drawable.uva))
        insertItem(Item(2, ItemTypes.FRUIT, "Pera", 0.55, R.drawable.pear))
        insertItem(Item(3, ItemTypes.FRUIT, "Naranja", 0.60, R.drawable.orange))

        insertItem(Item(4, ItemTypes.SPORT, "Chandal", 49.99, R.drawable.chandal))
        insertItem(Item(5, ItemTypes.SPORT, "Toalla", 19.99, R.drawable.toalla))
        insertItem(Item(6, ItemTypes.SPORT, "Zapatillas", 99.99, R.drawable.zapatillas))

        insertItem(Item(7, ItemTypes.BUTCHER, "Ternera", 2.75, R.drawable.ternera))
        insertItem(Item(8, ItemTypes.BUTCHER, "Pollo", 2.50, R.drawable.pollo))
        insertItem(Item(9, ItemTypes.BUTCHER, "Gallina", 2.99, R.drawable.gallina))

        insertItem(Item(10, ItemTypes.FISH, "Trucha", 3.3, R.drawable.trucha))
        insertItem(Item(11, ItemTypes.FISH, "Salmón", 5.0, R.drawable.salmon))
        insertItem(Item(12, ItemTypes.FISH, "Atún", 2.5, R.drawable.atun))

        // Launch work every 15 minutes
        val workRequest = PeriodicWorkRequestBuilder<ItemWorker>(PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS, TimeUnit.MILLISECONDS)
            .build()
        workManager.enqueue(workRequest)

        // observe on username change to update user orders list
        username.observeForever {
            orders = orderDao.getOrdersByUsername(it)
        }
    }
}
