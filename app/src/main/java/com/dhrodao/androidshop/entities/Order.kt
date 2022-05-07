package com.dhrodao.androidshop.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dhrodao.androidshop.util.BasketItem

@Entity
class Order (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var username: String,
    var totalPrice: Double,
    var orderedItems: List<BasketItem>
)