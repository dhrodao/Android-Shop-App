package com.dhrodao.androidshop.fruitshop

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhrodao.androidshop.util.BasketItem
import com.dhrodao.androidshop.items.BasketItems
import com.dhrodao.androidshop.items.ItemTypes
import com.dhrodao.androidshop.main.R
import com.dhrodao.androidshop.main.databinding.FragmentFruitShopBinding
import com.dhrodao.androidshop.util.*
import com.dhrodao.androidshop.viewmodel.MainViewModel
import com.dhrodao.androidshop.viewmodel.ShopViewModel

class FruitShopFragment() : Fragment() {
    private lateinit var mainLayout : ViewGroup
    private lateinit var quantityLayout : ViewGroup
    private lateinit var priceLayout : ViewGroup
    private lateinit var basketLayout : RecyclerView

    private lateinit var seekBar : SeekBar
    private lateinit var spinner : CustomSpinner
    private lateinit var addToBasketButton : Button
    private lateinit var progressValueTextView : TextView
    private lateinit var priceValueTextView : TextView
    private lateinit var totalValueTextView : TextView

    private lateinit var customShopRecyclerAdapter : CustomShopRecyclerAdapter
    private lateinit var customSeekBarListener: CustomSeekBarListener
    private lateinit var customSpinnerSelectorListener: CustomSpinnerSelectorListener

    private lateinit var viewModel: ShopViewModel

    private var binding: FragmentFruitShopBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Binding
        binding = FragmentFruitShopBinding.inflate(inflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
            .fruitShopViewModel
        binding?.viewModel = viewModel

        setViewModelObservers() // Set observers for the ViewModel

        initComponents() // initialize late init variables

        spinner.apply { // Spinner
            val itemArrayList = CorrespondingSpinnerItemsRetriever.getSpinnerItems(ItemTypes.FRUIT)
            adapter = CustomSpinnerAdapter(context, 0, itemArrayList)
            customSpinnerSelectorListener = CustomSpinnerSelectorListener(
                arrayOf(binding!!.quantityLayout, binding!!.priceLayout, binding!!.addBasketButton),
                itemArrayList,
                viewModel,
                seekBar
            )
            onItemSelectedListener = customSpinnerSelectorListener
            listener = OnSpinnerEventsListenerImpl()
        }

        seekBar.apply { // SeekBar
            customSeekBarListener = CustomSeekBarListener(viewModel)
            setOnSeekBarChangeListener(customSeekBarListener)
        }

        basketLayout.apply { // RecyclerView
            customShopRecyclerAdapter = CustomShopRecyclerAdapter(viewModel.fruitBasketItems.value!!)
            customShopRecyclerAdapter.setOnClickListener{ // Go to product details
                navigateToProductDetails(it)
            }
            adapter = customShopRecyclerAdapter
            layoutManager = LinearLayoutManager(context)
        }

        addToBasketButton.setOnClickListener { // Button
            if (viewModel.itemsQuantity.value!! > 0) {
                viewModel.updateBasketPrice()

                setBasketView()
                resetDefaultValues()
            }
        }

        spinner.setSelection(viewModel.currentSpinnerItem.value!!)

        return binding!!.root
    }

    private fun setViewModelObservers(){
        val computeFruitPriceObserver = createFruitPriceObserver()
        setComputeFruitQuantityObserver(computeFruitPriceObserver)

        val fruitQuantityObserver = createFruitQuantityObserver()
        setFruitQuantityObserver(fruitQuantityObserver)

        val basketPriceObserver = createBasketPriceObserver()
        setBasketPriceObserver(basketPriceObserver)
    }

    private fun setBasketPriceObserver(basketPriceObserver: Observer<Double>) {
        viewModel.basketPrice.observe(viewLifecycleOwner, basketPriceObserver)
    }

    private fun setFruitQuantityObserver(fruitQuantityObserver: Observer<Int>) {
        viewModel.itemsQuantity.observe(viewLifecycleOwner, fruitQuantityObserver)
    }

    private fun setComputeFruitQuantityObserver(computeFruitPriceObserver: Observer<Double>) {
        viewModel.computedItemsPrice.observe(viewLifecycleOwner, computeFruitPriceObserver)
    }

    private fun createBasketPriceObserver(): Observer<Double> {
        val basketPriceObserver = Observer<Double> {
            val totalText = "Total: %.2f €".format(it)
            totalValueTextView.text = totalText
        }
        return basketPriceObserver
    }

    private fun createFruitQuantityObserver(): Observer<Int> {
        val fruitQuantityObserver = Observer<Int> {
            val quantityText = "Cantidad: %d".format(it)
            progressValueTextView.text = quantityText
        }
        return fruitQuantityObserver
    }

    private fun createFruitPriceObserver(): Observer<Double> {
        val computeFruitPriceObserver = Observer<Double> {
            val totalText = "Total: %.2f €".format(it)
            priceValueTextView.text = totalText
        }
        return computeFruitPriceObserver
    }

    private fun initComponents() {
        mainLayout = binding!!.mainLayout
        quantityLayout = binding!!.quantityLayout
        priceLayout = binding!!.priceLayout
        basketLayout = binding!!.basketContainer

        progressValueTextView = binding!!.quantityDisplay
        priceValueTextView = binding!!.finalPrice
        totalValueTextView = binding!!.basketPrice

        addToBasketButton = binding!!.addBasketButton
        spinner = binding!!.itemSpinner
        seekBar = binding!!.quantitySeekbar
    }

    private fun setBasketView() {
        customSpinnerSelectorListener.currentBasketItem?.let {
            val basketItem = BasketItem(it.item, it.icon, viewModel.itemsQuantity.value!!, it.price)
            viewModel.addToBasket(basketItem)
            customShopRecyclerAdapter.notifyItemInserted(viewModel.getBasketSize() - 1)
        }
    }

    private fun resetDefaultValues() {
        viewModel.resetItems()
        spinner.setSelection(0)
    }

    private fun navigateToProductDetails(view: View) {
        val basketItem = viewModel.fruitBasketItems.value!![basketLayout.getChildAdapterPosition(view)]
        viewModel.setSelectedItem(basketItem)
        findNavController().navigate(FruitShopFragmentDirections.actionFruitShopFragmentToProductDetailsFragment(ItemTypes.FRUIT))
    }

    class OnSpinnerEventsListenerImpl : CustomSpinner.OnSpinnerEventsListener {
        override fun onDropdownOpened(spinner: AppCompatSpinner) {
            spinner.setBackgroundResource(R.drawable.spinner_bg_dropped)
        }

        override fun onDropdownClosed(spinner: AppCompatSpinner) {
            spinner.setBackgroundResource(R.drawable.spinner_bg)
        }
    }
}