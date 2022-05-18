package com.dhrodao.androidshop.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dhrodao.androidshop.entities.User

@Dao
interface UserDao {
    @Query("SELECT * FROM `User`")
    fun getUsers(): LiveData<List<User>>

    @Query("SELECT * FROM `User` WHERE username = :username")
    fun getUserByUsername(username: String): LiveData<User>

    @Insert
    suspend fun insertUser(vararg user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)
}