package com.dhrodao.androidshop.dao

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dhrodao.androidshop.viewmodel.MainViewModel

class ItemViewModelFactory(private val dao: ItemDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}