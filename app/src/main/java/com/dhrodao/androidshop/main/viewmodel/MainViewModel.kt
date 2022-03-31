package com.dhrodao.androidshop.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var userName: MutableLiveData<String> = MutableLiveData("")
    private var currentFragmentId: MutableLiveData<Int> = MutableLiveData(0)

    fun setUserName(name: String) {
        userName.value = name
    }

    fun getUserName(): String {
        return userName.value.toString()
    }

    fun setCurrentFragmentId(value: Int) {
        currentFragmentId.value = value
    }

    fun getCurrentFragmentId(): Int {
        return currentFragmentId.value!!
    }
}