package com.dhrodao.androidshop.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dhrodao.androidshop.entities.Item
import com.dhrodao.androidshop.items.ItemTypes

@Dao
interface ItemDao {
    @Query("SELECT * FROM items")
    fun getItems(): LiveData<List<Item>>

    @Query("SELECT * FROM items WHERE type = :type")
    fun getItemsByType(type: ItemTypes): LiveData<List<Item>>

    @Query("SELECT * FROM items WHERE id = :id")
    suspend fun getItemById(id: Int): Item

    @Query("SELECT * FROM items WHERE id IN (:itemIds)")
    fun loadAllByIds(itemIds: IntArray): List<Item>

    @Query("DELETE FROM items")
    suspend fun clear()

    @Insert
    suspend fun insertItem(vararg item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)
}