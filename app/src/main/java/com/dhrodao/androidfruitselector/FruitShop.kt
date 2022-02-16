package com.dhrodao.androidfruitselector

class FruitShop(private val manager: FruitShopManager) {
    var fruitPrice : Double = 0.00
    var computedFruitPrice : Double = 0.00
        set(value) {
            field = value
            manager.updateFruitPriceText(value)
        }
    var fruitQuantity : Int = 0
        set(value) {
            field = value
            manager.updateFruitQuantityText(value)
        }
    var basketPrice : Double = 0.00
        set(value) {
            field = value
            manager.updateBasketPriceText(value)
        }
    val basketItems: ArrayList<BasketItem> = ArrayList()

    fun updateFruitPrice() {
        computedFruitPrice = fruitPrice * fruitQuantity
    }

    fun updateBasketPrice() {
        basketPrice += fruitQuantity.toDouble() * fruitPrice
    }

    fun resetFruit() {
        fruitQuantity = 0
        computedFruitPrice = 0.00
    }

    fun getBasketSize() : Int {
        return basketItems.size
    }

    fun addToBasket(item: BasketItem) {
        basketItems.add(item)
    }
}