package com.dhrodao.androidshop.sportsshop

import android.os.Bundle
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
import com.dhrodao.androidshop.main.R
import com.dhrodao.androidshop.main.databinding.FragmentSportsShopBinding
import com.dhrodao.androidshop.util.*
import com.dhrodao.androidshop.viewmodel.MainViewModel
import com.dhrodao.androidshop.viewmodel.ShopViewModel

class SportsShopFragment : Fragment() {

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

    private var binding: FragmentSportsShopBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Binding
        binding = FragmentSportsShopBinding.inflate(inflater, container, false)

        // ViewModel
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
            .sportsShopViewModel
        binding?.viewModel = viewModel

        setup()

        return binding!!.root
    }

    private fun setup() {
        setViewModelObservers()

        initComponents()

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
        basketLayout.apply { // RecyclerView
            customShopRecyclerAdapter =
                CustomShopRecyclerAdapter(viewModel.allBasketItems.value!!)
            customShopRecyclerAdapter.setOnClickListener { // Go to product details
                navigateToProductDetails(it)
            }
            adapter = customShopRecyclerAdapter
            layoutManager = LinearLayoutManager(context)
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
            val itemArrayList = CorrespondingSpinnerItemsRetriever.getSpinnerItems(viewModel.itemType)
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
        val basketItem = viewModel.allBasketItems.value!![basketLayout.getChildAdapterPosition(view)]
        viewModel.setSelectedItem(basketItem)
        findNavController().navigate(SportsShopFragmentDirections.actionSportsShopFragmentToProductDetailsFragment(viewModel.itemType))
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