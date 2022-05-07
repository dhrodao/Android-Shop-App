package com.dhrodao.androidshop.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromArrayList(list: List<BasketItem>): String {
        val gson = Gson()
        val type = object : TypeToken<ArrayList<BasketItem>>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun toArrayList(json: String): List<BasketItem> {
        val gson = Gson()
        val type = object : TypeToken<List<BasketItem>>() {}.type
        return gson.fromJson(json, type)
    }
}