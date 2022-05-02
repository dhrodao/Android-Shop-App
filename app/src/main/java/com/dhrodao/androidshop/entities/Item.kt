package com.dhrodao.androidshop.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dhrodao.androidshop.items.ItemTypes

@Entity(tableName = "items")
data class Item (
    @PrimaryKey(autoGenerate = true) var id : Int,
    @ColumnInfo(name = "type") var type : ItemTypes,
    @ColumnInfo(name = "name") var name : String,
    @ColumnInfo(name = "price") var price : Double,
    @ColumnInfo(name = "icon") var icon : Int
    )