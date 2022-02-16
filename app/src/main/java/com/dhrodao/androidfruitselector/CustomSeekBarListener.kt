package com.dhrodao.androidfruitselector

import android.widget.SeekBar

class CustomSeekBarListener(private val fruitShop: FruitShop
) : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        fruitShop.fruitQuantity = progress
        fruitShop.updateFruitPrice()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}