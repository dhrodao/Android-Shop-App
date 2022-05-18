package com.dhrodao.androidshop.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dhrodao.androidshop.entities.Item
import com.dhrodao.androidshop.entities.Order
import com.dhrodao.androidshop.entities.User
import com.dhrodao.androidshop.util.Converters

@Database(entities = [Item::class, Order::class, User::class], version = 6)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val itemDao: ItemDao
    abstract val orderDao: OrderDao
    abstract val userDao: UserDao

    // Get database singleton instance
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "App_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}