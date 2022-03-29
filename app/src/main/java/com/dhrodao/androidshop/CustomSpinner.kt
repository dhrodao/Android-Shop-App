package com.dhrodao.androidshop

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatSpinner

class CustomSpinner(context: Context, attrs: AttributeSet) : AppCompatSpinner(context, attrs) {
    interface OnSpinnerEventsListener {
        fun onDropdownOpened(spinner : AppCompatSpinner)
        fun onDropdownClosed(spinner : AppCompatSpinner)
    }

    var listener : OnSpinnerEventsListener? = null

    private var dropdownOpen = false

    private val TAG = "CustomSpinnerClass"

    override fun performClick(): Boolean {
        Log.d(TAG, "performClick")

        performOpenEvent()

        return super.performClick()
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        Log.d(TAG, "onWindowFocusChanged")

        if (hasWindowFocus && dropdownOpen) {
            performCloseEvent()
        }
    }

    private fun performOpenEvent() {
        dropdownOpen = true
        listener?.onDropdownOpened(this)
    }

    private fun performCloseEvent() {
        dropdownOpen = false
        listener?.onDropdownClosed(this)
    }
}