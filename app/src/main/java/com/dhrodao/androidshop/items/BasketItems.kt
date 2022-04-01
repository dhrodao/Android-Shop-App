package com.dhrodao.androidshop.items

import com.dhrodao.androidshop.main.R



enum class BasketItems(val type: ItemTypes, val item : String, val icon : Int, val price : Double) {
    PINEAPPLE(ItemTypes.FRUIT, "Piña", R.drawable.pineapple, 1.5),
    UVA(ItemTypes.FRUIT, "Uva", R.drawable.uva, 0.75),
    PEAR(ItemTypes.FRUIT, "Pera", R.drawable.pear, 0.55),
    ORANGE(ItemTypes.FRUIT, "Naranja", R.drawable.orange, 0.60),

    CHANDAL(ItemTypes.SPORT, "Chandal", R.drawable.orange, 49.99),
    ZAPATILLAS(ItemTypes.SPORT, "Zapatillas", R.drawable.orange, 99.99),
    TOALLA(ItemTypes.SPORT, "Toalla", R.drawable.orange, 19.99),


}