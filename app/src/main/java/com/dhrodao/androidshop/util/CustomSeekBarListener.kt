package com.dhrodao.androidshop.util

import android.widget.SeekBar
import com.dhrodao.androidshop.fruitshop.viewmodel.FruitShopViewModel

class CustomSeekBarListener(private val fruitShopViewModel: FruitShopViewModel
) : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        fruitShopViewModel.updateFruitQuantity(progress)
        fruitShopViewModel.updateFruitPrice()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}