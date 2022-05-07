package com.dhrodao.androidshop.dao

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dhrodao.androidshop.viewmodel.MainViewModel

class ItemViewModelFactory(private val application: Application, private val itemDao: ItemDao, private val orderDao: OrderDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application, itemDao, orderDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}