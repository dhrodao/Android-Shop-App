package com.dhrodao.androidshop.fragment

import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhrodao.androidshop.entities.Item
import com.dhrodao.androidshop.main.R
import com.dhrodao.androidshop.util.*
import com.dhrodao.androidshop.viewmodel.ShopViewModel

abstract class ShopFragment<B: ViewDataBinding>(layoutId: Int) : BaseFragment<ShopViewModel, B>(layoutId) {
    protected lateinit var mainLayout : ViewGroup
    protected lateinit var quantityLayout : ViewGroup
    protected lateinit var priceLayout : ViewGroup
    protected lateinit var basketLayout : RecyclerView

    protected lateinit var seekBar : SeekBar
    protected lateinit var spinner : CustomSpinner
    protected lateinit var addToBasketButton : Button
    protected lateinit var progressValueTextView : TextView
    protected lateinit var priceValueTextView : TextView
    protected lateinit var totalValueTextView : TextView

    private lateinit var customShopRecyclerAdapter : CustomShopRecyclerAdapter
    private lateinit var customSeekBarListener: CustomSeekBarListener
    private lateinit var customSpinnerSelectorListener: CustomSpinnerSelectorListener

    protected lateinit var affectedUIItems: Array<View>

    protected fun setup() {
        setViewModelObservers() // Set observers for the ViewModel

        initComponents() // initialize late init variables

        setupSpinner()

        setupSeekBar()

        setupBasketLayout()

        setupAddToBasketButton()

        spinner.setSelection(viewModel.currentSpinnerItem.value!!)
    }

    private fun setupAddToBasketButton() {
        addToBasketButton.setOnClickListener { // Button
            if (viewModel.itemsQuantity.value!! > 0) {
                viewModel.updateBasketPrice()

                setBasketView()
                resetDefaultValues()
            }
        }
    }

    private fun setupBasketLayout() {
        val fragment = this
        basketLayout.apply { // RecyclerView
            customShopRecyclerAdapter =
                CustomShopRecyclerAdapter(fragment, viewModel.getBasketItems(), viewModel.getGlobalBasketItems(), viewModel)
            customShopRecyclerAdapter.setOnClickListener { // Go to product details
                navigateToProductDetails(it)
            }
            adapter = customShopRecyclerAdapter
            layoutManager = LinearLayoutManager(context)

            val swipeToDeleteCallback = SwipeToDeleteCallback(customShopRecyclerAdapter, viewModel)
            val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
            itemTouchHelper.attachToRecyclerView(this)
        }
    }

    private fun setupSeekBar() {
        seekBar.apply { // SeekBar
            customSeekBarListener = CustomSeekBarListener(viewModel)
            setOnSeekBarChangeListener(customSeekBarListener)
        }
    }

    private fun setupSpinner() {
        spinner.apply { // Spinner
            val itemArrayList = ArrayList<Item>()
                //CorrespondingSpinnerItemsRetriever.getSpinnerItems(viewModel.itemType)
            adapter = CustomSpinnerAdapter(context, 0, itemArrayList)
            customSpinnerSelectorListener = CustomSpinnerSelectorListener(
                affectedUIItems,
                itemArrayList,
                viewModel,
                seekBar
            )
            onItemSelectedListener = customSpinnerSelectorListener
            listener = OnSpinnerEventsListenerImpl()
        }
    }

    private fun setViewModelObservers(){
        val computeItemPriceObserver = createItemPriceObserver()
        setComputeItemQuantityObserver(computeItemPriceObserver)

        val itemQuantityObserver = createItemQuantityObserver()
        setItemQuantityObserver(itemQuantityObserver)

        val basketPriceObserver = createBasketPriceObserver()
        setBasketPriceObserver(basketPriceObserver)
    }

    private fun setBasketPriceObserver(basketPriceObserver: Observer<Double>) {
        viewModel.basketPrice.observe(viewLifecycleOwner, basketPriceObserver)
    }

    private fun setItemQuantityObserver(itemQuantityObserver: Observer<Int>) {
        viewModel.itemsQuantity.observe(viewLifecycleOwner, itemQuantityObserver)
    }

    private fun setComputeItemQuantityObserver(computeItemPriceObserver: Observer<Double>) {
        viewModel.computedItemsPrice.observe(viewLifecycleOwner, computeItemPriceObserver)
    }

    private fun createBasketPriceObserver(): Observer<Double> {
        val basketPriceObserver = Observer<Double> {
            val totalText = "Total: %.2f €".format(it)
            totalValueTextView.text = totalText
        }
        return basketPriceObserver
    }

    private fun createItemQuantityObserver(): Observer<Int> {
        val itemQuantityObserver = Observer<Int> {
            val quantityText = "Cantidad: %d".format(it)
            progressValueTextView.text = quantityText
        }
        return itemQuantityObserver
    }

    private fun createItemPriceObserver(): Observer<Double> {
        val computeItemPriceObserver = Observer<Double> {
            val totalText = "Total: %.2f €".format(it)
            priceValueTextView.text = totalText
        }
        return computeItemPriceObserver
    }

    abstract fun initComponents()

    private fun setBasketView() {
        customSpinnerSelectorListener.currentBasketItem?.let {
            val basketItem = BasketItem(it.name, it.icon, viewModel.itemsQuantity.value!!, it.price)

            //viewModel.addToBasket(basketItem)
            customShopRecyclerAdapter.addItem(basketItem)
        }
    }

    private fun resetDefaultValues() {
        viewModel.resetItems()
        spinner.setSelection(0)
    }

    abstract fun navigateToProductDetails(view: View)

    class OnSpinnerEventsListenerImpl : CustomSpinner.OnSpinnerEventsListener {
        override fun onDropdownOpened(spinner: AppCompatSpinner) {
            spinner.setBackgroundResource(R.drawable.spinner_bg_dropped)
        }

        override fun onDropdownClosed(spinner: AppCompatSpinner) {
            spinner.setBackgroundResource(R.drawable.spinner_bg)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}