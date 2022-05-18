package com.dhrodao.androidshop.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dhrodao.androidshop.entities.Order

@Dao
interface OrderDao {
    @Query("SELECT * FROM `order`")
    fun getOrders(): LiveData<List<Order>>

    @Query("SELECT * FROM `order` WHERE username = :username")
    fun getOrdersByUsername(username: String): LiveData<List<Order>>

    @Insert
    suspend fun insertOrder(vararg order: Order)

    @Update
    suspend fun update(order: Order)

    @Delete
    suspend fun delete(order: Order)
}