package com.dhrodao.androidshop.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.dhrodao.androidshop.ItemService.Api
import com.dhrodao.androidshop.dao.AppDatabase
import com.dhrodao.androidshop.entities.Item
import com.dhrodao.androidshop.items.ItemTypes
import kotlinx.coroutines.runBlocking
import java.util.*

class ItemWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {
    val itemDao = AppDatabase.getInstance(applicationContext).itemDao

    override fun doWork(): Result {
        Log.d("ItemWorker", "doWork")
        runBlocking {
            // Update Prices
            try {
                val items = Api.retrofitService.getItems()
                items.forEach {
                    val dbItem = itemDao.getItemById(it.id)
                    if (dbItem != null) {
                        dbItem.price = it.price
                        Log.d("posts", dbItem.toString())
                        itemDao.update(dbItem)
                    }else{
                        val type: ItemTypes = ItemTypes.valueOf(it.type.uppercase(Locale.getDefault()))
                        if (type != null) {
                            val insertItem = Item(it.id, type, it.name, it.price, 0)
                            Log.d("posts: insert:", insertItem.toString())
                            itemDao.insertItem(insertItem)
                        }
                    }
                }
            }catch (e: Exception){
                Log.e("ItemWorker", "Error: ${e.message}")
                return@runBlocking Result.failure()
            }
        }
        return Result.success()
    }
}