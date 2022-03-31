package com.dhrodao.androidshop.fruitshop

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

    private lateinit var customShopRecyclerAdapter : CustomShopRecyclerAdapter
    private lateinit var customSeekBarListener: CustomSeekBarListener
    private lateinit var customSpinnerSelectorListener: CustomSpinnerSelectorListener

    private lateinit var fruitShopViewModel: FruitShopViewModel

    private var binding: FragmentFruitShopBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Binding
        binding = FragmentFruitShopBinding.inflate(inflater, container, false)

        // ViewModel
        fruitShopViewModel = ViewModelProvider(requireActivity())[FruitShopViewModel::class.java]
        binding?.viewModel = fruitShopViewModel

        setViewModelObservers() // Set observers for the ViewModel

        initComponents() // initialize late init variables

        spinner.apply { // Spinner
            adapter = CustomSpinnerAdapter(context, 0, FruitItems.values())
            customSpinnerSelectorListener = CustomSpinnerSelectorListener(
                binding!!,
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
            customShopRecyclerAdapter = CustomShopRecyclerAdapter(fruitShopViewModel.basketItems.value!!)
            customShopRecyclerAdapter.setOnClickListener{ // Go to product details
                navigateToProductDetails(it)
            }
            adapter = customShopRecyclerAdapter
            layoutManager = LinearLayoutManager(context)
        }

        addToBasketButton.setOnClickListener { // Button
            if (fruitShopViewModel.fruitQuantity.value!! > 0) {
                fruitShopViewModel.updateBasketPrice()

                setBasketView()
                resetDefaultValues()
            }
        }

        spinner.setSelection(fruitShopViewModel.currentSpinnerItem.value!!)

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
        fruitShopViewModel.basketPrice.observe(viewLifecycleOwner, basketPriceObserver)
    }

    private fun setFruitQuantityObserver(fruitQuantityObserver: Observer<Int>) {
        fruitShopViewModel.fruitQuantity.observe(viewLifecycleOwner, fruitQuantityObserver)
    }

    private fun setComputeFruitQuantityObserver(computeFruitPriceObserver: Observer<Double>) {
        fruitShopViewModel.computedFruitPrice.observe(viewLifecycleOwner, computeFruitPriceObserver)
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
        spinner = binding!!.fruitSpinner
        seekBar = binding!!.quantitySeekbar
    }

    private fun setBasketView() {
        customSpinnerSelectorListener.currentFruitItem?.let {
            val basketItem = BasketItem(it.fruit, it.icon, fruitShopViewModel.fruitQuantity.value!!, it.price)
            fruitShopViewModel.addToBasket(basketItem)
            customShopRecyclerAdapter.notifyItemInserted(fruitShopViewModel.getBasketSize() - 1)
        }
    }

    private fun resetDefaultValues() {
        fruitShopViewModel.resetFruit()
        spinner.setSelection(0)
    }

    private fun navigateToProductDetails(view: View) {
        val basketItem = fruitShopViewModel.basketItems.value!![basketLayout.getChildAdapterPosition(view)]
        fruitShopViewModel.setFruitItemSelected(basketItem)
        findNavController().navigate(FruitShopFragmentDirections.actionFruitShopFragmentToProductDetailsFragment())
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