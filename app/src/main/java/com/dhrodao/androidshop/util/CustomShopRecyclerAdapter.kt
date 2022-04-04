package com.dhrodao.androidshop.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.dhrodao.androidshop.main.R
import com.dhrodao.androidshop.viewmodel.ShopViewModel
import com.google.android.material.snackbar.Snackbar

class CustomShopRecyclerAdapter(private val fragment: Fragment, private val dataSet : ArrayList<BasketItem>, private val globalDataset: ArrayList<BasketItem>?, private val viewModel: ShopViewModel?) : RecyclerView.Adapter<CustomShopRecyclerAdapter.DataViewHolder>(), View.OnClickListener {
    private var listener: View.OnClickListener? = null

    private val backupDataSet = ArrayList(dataSet)
    private var recentlyRemovedItem : BasketItem? = null
    private var recentlyRemovedItemPositionBackup : Int? = null
    private var recentlyRemovedItemPositionDataSet : Int? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder {
        val basketView : View = LayoutInflater.from(parent.context).inflate(R.layout.item_fruit_basket, parent, false)

        basketView.setOnClickListener(this)

        return DataViewHolder(basketView)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.setData(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class DataViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.findViewById<ImageView>(R.id.image)
        private val textView = itemView.findViewById<TextView>(R.id.text)
        private val quantityText = itemView.findViewById<TextView>(R.id.fruit_quantity)

        fun setData(basketItem: BasketItem) {
            image.setImageResource(basketItem.icon)
            textView.text = basketItem.item

            val auxText = "x${basketItem.quantity}"
            quantityText.text = auxText
        }
    }

    fun addItem(item : BasketItem) {
        backupDataSet.add(item)
        dataSet.add(item)
        globalDataset?.add(item)

        notifyItemInserted(dataSet.size - 1)
    }

    fun deleteItem(position : Int) {
        // Save deleted item
        recentlyRemovedItem = dataSet[position]
        recentlyRemovedItemPositionBackup = backupDataSet.indexOf(recentlyRemovedItem)
        recentlyRemovedItemPositionDataSet = dataSet.indexOf(recentlyRemovedItem)

        // Delete item from lists
        dataSet.remove(recentlyRemovedItem)
        globalDataset?.remove(recentlyRemovedItem)
        backupDataSet.removeAt(position)

        // Notify dataset changed
        notifyItemRemoved(recentlyRemovedItemPositionDataSet!!)

        // Show SnackBar and Undo message
        showUndoMessage()
    }

    private fun showUndoMessage() {
        fragment.view?.findViewById<View>(R.id.mainLayout)?.let {
            val snackBar = Snackbar.make(it, R.string.snack_bar_text, Snackbar.LENGTH_LONG)
            snackBar.setAction(R.string.snack_bar_undo_text) { undoDelete() }
            snackBar.show()
        }
    }

    private fun undoDelete(){
        recentlyRemovedItem?.let {
            // Recover basket prices
            val price = recentlyRemovedItem!!.pricePerItem
            val quantity = recentlyRemovedItem!!.quantity
            val totalPrice = price * quantity

            val finalBasketPrice = viewModel?.basketPrice?.value!! + totalPrice
            val finalGlobalBasketPrice = viewModel.globalBasketPrice.value!! + totalPrice
            viewModel.setBasketPrice(finalBasketPrice)
            viewModel.setGlobalBasketPrice(finalGlobalBasketPrice)

            // Insert item
            backupDataSet.add(recentlyRemovedItemPositionBackup!!, recentlyRemovedItem)
            dataSet.add(recentlyRemovedItemPositionDataSet!!, recentlyRemovedItem!!)
            globalDataset?.add(recentlyRemovedItemPositionDataSet!!, recentlyRemovedItem!!)

            // Notify to refresh view
            notifyItemInserted(recentlyRemovedItemPositionDataSet!!)
        }
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        this.listener = listener
    }

    override fun onClick(view: View?) {
        this.listener?.onClick(view)
    }

    fun getRecentlyRemovedPrice() : Double {
        recentlyRemovedItem?.let {
            return it.quantity * it.pricePerItem
        }
        return 0.0
    }
}