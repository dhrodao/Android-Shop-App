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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhrodao.androidshop.*
import com.dhrodao.androidshop.main.R

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

    private lateinit var fruitShop: FruitShop

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("FruitSelector", "onCreateView")

        val view = inflater.inflate(R.layout.fragment_fruit_shop, container, false)

        initComponents(view) // initialize late init variables

        spinner.apply { // Spinner
            adapter = CustomSpinnerAdapter(context, 0, FruitItems.values())
            customSpinnerSelectorListener = CustomSpinnerSelectorListener(
                arrayOf(quantityLayout, priceLayout, addToBasketButton),
                FruitItems.values(),
                fruitShop,
                seekBar
            )
            onItemSelectedListener = customSpinnerSelectorListener
            listener = OnSpinnerEventsListenerImpl()
        }

        seekBar.apply { // SeekBar
            customSeekBarListener = CustomSeekBarListener(fruitShop)
            setOnSeekBarChangeListener(customSeekBarListener)
        }

        basketLayout.apply { // RecyclerView
            customRecyclerAdapter = CustomRecyclerAdapter(fruitShop.basketItems)
            adapter = customRecyclerAdapter
            layoutManager = LinearLayoutManager(context)
        }

        addToBasketButton.setOnClickListener { // Button
            if (fruitShop.fruitQuantity > 0) {
                fruitShop.updateBasketPrice()

                setBasketView()
                resetDefaultValues()
            }
        }

        savedInstanceState?.let{ restoreState(it) } // recover screen status if any

        // Inflate the layout for this fragment
        return view
    }

    private fun restoreState(outState: Bundle) {
        Log.d("FruitSelector", "onRestoreInstanceState")

        customSpinnerSelectorListener.onRestore = true

        outState.let {
            it.getParcelableArrayList<BasketItem>("basketRecyclerViewItems").also { restoredParcelable ->
                if (restoredParcelable != null) {
                    for (item in restoredParcelable){
                        fruitShop.addToBasket(item)

                        val index = fruitShop.getBasketSize() - 1
                        customRecyclerAdapter.notifyItemInserted(index)
                    }
                }
            }
            it.getInt("currentSpinnerItem").also { selection -> spinner.setSelection(selection) }
            it.getDouble("fruitPrice").also { price -> fruitShop.fruitPrice = price }
            it.getDouble("computedFruitPrice").also { computedPrice -> fruitShop.computedFruitPrice = computedPrice }
            it.getInt("fruitQuantity").also { quantity -> fruitShop.fruitQuantity = quantity }
            it.getDouble("basketPrice").also { price -> fruitShop.basketPrice = price }
            it.getInt("currentProgress").also { progress -> seekBar.progress = progress }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("FruitSelector", "onSaveInstanceState")

        outState.putParcelableArrayList("basketRecyclerViewItems", fruitShop.basketItems)
        outState.putInt("currentSpinnerItem", spinner.selectedItemPosition)
        outState.putDouble("fruitPrice", fruitShop.fruitPrice)
        outState.putDouble("computedFruitPrice", fruitShop.computedFruitPrice)
        outState.putDouble("basketPrice", fruitShop.basketPrice)
        outState.putInt("fruitQuantity", fruitShop.fruitQuantity)
        outState.putInt("currentProgress", seekBar.progress)
    }

    private fun initComponents(view: View) {
        mainLayout = view.findViewById(R.id.mainLayout)
        quantityLayout = view.findViewById(R.id.quantity_layout)
        priceLayout = view.findViewById(R.id.price_layout)
        basketLayout = view.findViewById(R.id.basket_container)

        progressValueTextView = view.findViewById(R.id.quantity_display)
        priceValueTextView = view.findViewById(R.id.final_price)
        totalValueTextView = view.findViewById(R.id.basket_price)

        val fruitShopManager = FruitShopManager(priceValueTextView,
            progressValueTextView, totalValueTextView)
        fruitShop = FruitShop(fruitShopManager)

        spinner = view.findViewById(R.id.fruit_spinner)
        seekBar = view.findViewById(R.id.quantity_seekbar)
        addToBasketButton = view.findViewById(R.id.add_basket_button)
    }

    private fun setBasketView() {
        customSpinnerSelectorListener.currentFruitItem?.let {
            val basketItem = BasketItem(it.fruit, it.icon, fruitShop.fruitQuantity)
            fruitShop.addToBasket(basketItem)
            customRecyclerAdapter.notifyItemInserted(fruitShop.getBasketSize() - 1)
        }
    }

    private fun resetDefaultValues() {
        fruitShop.resetFruit()
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