package com.dhrodao.androidshop.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var userName: MutableLiveData<String> = MutableLiveData("")

    fun setUserName(name: String) {
        userName.value = name
    }

    fun getUserName(): String {
        return userName.value.toString()
    }
}