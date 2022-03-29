package com.dhrodao.androidshop.main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val currentFragment = MutableLiveData<Int>(0)

    private var userName: MutableLiveData<String> = MutableLiveData("")

    fun setUserName(name: String) {
        userName.value = name
    }

    fun getUserName(): String {
        return userName.value.toString()
    }

    fun setCurrentFragment(fragment: Int) {
        currentFragment.value = fragment
    }

    fun getCurrentFragment(): Int {
        return currentFragment.value ?: 0
    }
}