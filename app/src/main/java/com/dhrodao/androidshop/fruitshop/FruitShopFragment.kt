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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhrodao.androidshop.fruitshop.viewmodel.FruitShopViewModel
import com.dhrodao.androidshop.util.BasketItem
import com.dhrodao.androidshop.items.FruitItems
import com.dhrodao.androidshop.main.R
import com.dhrodao.androidshop.main.databinding.FragmentFruitShopBinding
import com.dhrodao.androidshop.util.*

class FruitShopFragment : Fragment() {
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

    private lateinit var customRecyclerAdapter : CustomRecyclerAdapter
    private lateinit var customSeekBarListener: CustomSeekBarListener
    private lateinit var customSpinnerSelectorListener: CustomSpinnerSelectorListener

    private lateinit var fruitShopViewModel: FruitShopViewModel

    private var binding: FragmentFruitShopBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("FruitSelector", "onCreateView")

        // Binding
        binding = FragmentFruitShopBinding.inflate(inflater, container, false)

        // ViewModel
        fruitShopViewModel = ViewModelProvider(this)[FruitShopViewModel::class.java]
        binding?.viewModel = fruitShopViewModel

        setViewModelObservers() // Set observers for the ViewModel

        initComponents() // initialize late init variables

        spinner.apply { // Spinner
            adapter = CustomSpinnerAdapter(context, 0, FruitItems.values())
            customSpinnerSelectorListener = CustomSpinnerSelectorListener(
                arrayOf(quantityLayout, priceLayout, addToBasketButton),
                FruitItems.values(),
                fruitShopViewModel,
                seekBar
            )
            onItemSelectedListener = customSpinnerSelectorListener
            listener = OnSpinnerEventsListenerImpl()
        }

        seekBar.apply { // SeekBar
            customSeekBarListener = CustomSeekBarListener(fruitShopViewModel)
            setOnSeekBarChangeListener(customSeekBarListener)
        }

        basketLayout.apply { // RecyclerView
            customRecyclerAdapter = CustomRecyclerAdapter(fruitShopViewModel.basketItems.value!!)
            adapter = customRecyclerAdapter
            layoutManager = LinearLayoutManager(context)
        }

        addToBasketButton.setOnClickListener { // Button
            if (fruitShopViewModel.fruitQuantity.value!! > 0) {
                fruitShopViewModel.updateBasketPrice()

                setBasketView()
                resetDefaultValues()
            }
        }

        customSpinnerSelectorListener.onRestore = true // Restore spinner state

        return binding!!.root
    }

    private fun setViewModelObservers(){
        val computeFruitPriceObserver = Observer<Double> {
            val totalText = "Total: %.2f €".format(it)
            priceValueTextView.text = totalText
        }
        fruitShopViewModel.computedFruitPrice.observe(viewLifecycleOwner, computeFruitPriceObserver)

        val fruitQuantityObserver = Observer<Int> {
            val quantityText = "Cantidad: %d".format(it)
            progressValueTextView.text = quantityText
        }
        fruitShopViewModel.fruitQuantity.observe(viewLifecycleOwner, fruitQuantityObserver)

        val basketPriceObserver = Observer<Double> {
            val totalText = "Total: %.2f €".format(it)
            totalValueTextView.text = totalText
        }
        fruitShopViewModel.basketPrice.observe(viewLifecycleOwner, basketPriceObserver)
    }

    private fun initComponents() {
        mainLayout = binding!!.mainLayout
        quantityLayout = binding!!.quantityLayout
        priceLayout = binding!!.priceLayout
        basketLayout = binding!!.basketContainer

        progressValueTextView = binding!!.quantityDisplay
        priceValueTextView = binding!!.finalPrice
        totalValueTextView = binding!!.basketPrice

        spinner = binding!!.fruitSpinner
        seekBar = binding!!.quantitySeekbar
        addToBasketButton = binding!!.addBasketButton
    }

    private fun setBasketView() {
        customSpinnerSelectorListener.currentFruitItem?.let {
            val basketItem = BasketItem(it.fruit, it.icon, fruitShopViewModel.fruitQuantity.value!!)
            fruitShopViewModel.addToBasket(basketItem)
            customRecyclerAdapter.notifyItemInserted(fruitShopViewModel.getBasketSize() - 1)
        }
    }

    private fun resetDefaultValues() {
        fruitShopViewModel.resetFruit()
        spinner.setSelection(0)
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