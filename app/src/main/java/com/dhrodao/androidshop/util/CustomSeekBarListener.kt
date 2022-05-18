package com.dhrodao.androidshop.util

import android.widget.SeekBar
import com.dhrodao.androidshop.viewmodel.ShopViewModel

class CustomSeekBarListener(private val fruitShopViewModel: ShopViewModel
) : SeekBar.OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        fruitShopViewModel.updateItemsQuantity(progress)
        fruitShopViewModel.updatePrice()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}