package com.dhrodao.androidshop.items

import com.dhrodao.androidshop.main.R



enum class BasketItems(val type: ItemTypes, val item : String, val icon : Int, val price : Double) {
    PINEAPPLE(ItemTypes.FRUIT, "Piña", R.drawable.pineapple, 1.5),
    UVA(ItemTypes.FRUIT, "Uva", R.drawable.uva, 0.75),
    PEAR(ItemTypes.FRUIT, "Pera", R.drawable.pear, 0.55),
    ORANGE(ItemTypes.FRUIT, "Naranja", R.drawable.orange, 0.60),

    CHANDAL(ItemTypes.SPORT, "Chandal", R.drawable.chandal, 49.99),
    ZAPATILLAS(ItemTypes.SPORT, "Zapatillas", R.drawable.zapatillas, 99.99),
    TOALLA(ItemTypes.SPORT, "Toalla", R.drawable.toalla, 19.99),

    TERNERA(ItemTypes.BUTCHER, "Ternera", R.drawable.ternera, 2.75),
    POLLO(ItemTypes.BUTCHER, "Pollo", R.drawable.pollo, 2.50),
    GALLINA(ItemTypes.BUTCHER, "Gallina", R.drawable.gallina, 2.99),

    TRUCHA(ItemTypes.FISH, "Trucha", R.drawable.trucha, 3.3),
    SALMON(ItemTypes.FISH, "Salmón", R.drawable.salmon, 5.0),
    ATUN(ItemTypes.FISH, "Atún", R.drawable.atun, 2.5),
}