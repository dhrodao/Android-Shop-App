package com.dhrodao.androidshop.util

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.dhrodao.androidshop.viewmodel.ShopViewModel

class SwipeToDeleteCallback(private val adapter: CustomShopRecyclerAdapter, private var viewModel: ShopViewModel) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // Swipe Left to delete
        if (direction == ItemTouchHelper.LEFT){
            // Delete item from TodoList
            val position = viewHolder.adapterPosition
            adapter.deleteItem(position)

            val priceDeleted = adapter.getRecentlyRemovedPrice()
            val currBasketPrice = viewModel.basketPrice.value!!
            val currGlobalBasketPrice = viewModel.globalBasketPrice.value!!

            val newBasketPrice = currBasketPrice - priceDeleted
            val newGlobalBasketPrice = currGlobalBasketPrice - priceDeleted

            viewModel.setBasketPrice(newBasketPrice)
            viewModel.setGlobalBasketPrice(newGlobalBasketPrice)
        }
    }
}