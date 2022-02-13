package com.dhrodao.androidfruitselector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

open class MainActivity : AppCompatActivity() {
    private lateinit var mainLayout : ViewGroup
    private lateinit var quantityLayout : ViewGroup
    private lateinit var priceLayout : ViewGroup
    private lateinit var basketLayout : RecyclerView

    private lateinit var seekBar : SeekBar
    private lateinit var spinner : CustomSpinner
    private lateinit var addToBasketButton : Button
    private lateinit var progressValueTextView : TextView
    private lateinit var priceValueTextView : TextView

    private var currentFruitItem : FruitItems? = null

    private var fruitPrice : Double = 0.00
    private var computedFruitPrice : Double = 0.00
    private var fruitQuantity : Int = 0
    private var basketPrice : Double = 0.00

    private var basketItems : ArrayList<BasketItem> = ArrayList()

    private lateinit var customRecyclerAdapter : CustomRecyclerAdapter // Dynamic list so need to access from methods

    data class BasketItem(val fruit : String, val icon : Int, val quantity : Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents() // initialize late init variables

        spinner = findViewById<CustomSpinner>(R.id.fruit_spinner).apply { // Spinner
            adapter = CustomSpinnerAdapter(this@MainActivity, 0, FruitItems.values())
            onItemSelectedListener = SpinnerSelectorListener(
                arrayOf(quantityLayout, priceLayout, addToBasketButton),
                FruitItems.values()
            )
            listener = OnSpinnerEventsListenerImpl()
        }

        seekBar = findViewById<SeekBar>(R.id.quantity_seekbar).apply {
            setOnSeekBarChangeListener(CustomSeekBarListener())
        }

        customRecyclerAdapter = CustomRecyclerAdapter(basketItems)
        basketLayout.adapter = customRecyclerAdapter
        basketLayout.layoutManager = LinearLayoutManager(this)

        addToBasketButton.setOnClickListener {
            if (fruitQuantity > 0) {
                setBasketView()
                updateBasketPrice()
                updateBasketPriceText()
                resetDefaultValues()
            }
        }
    }

    private fun initComponents() {
        mainLayout = findViewById(R.id.mainLayout)
        quantityLayout = findViewById(R.id.quantity_layout)
        priceLayout = findViewById(R.id.price_layout)
        basketLayout = findViewById(R.id.basket_container)

        progressValueTextView = findViewById(R.id.quantity_display)
        priceValueTextView = findViewById(R.id.final_price)

        addToBasketButton = findViewById(R.id.add_basket_button)
    }

    private fun setBasketView() {
        currentFruitItem?.let {
            val basketItem = BasketItem(it.fruit, it.icon, fruitQuantity)
            basketItems.add(basketItem)
            customRecyclerAdapter.notifyItemInserted(basketItems.size - 1)
        }
    }

    private fun updateBasketPrice() {
        basketPrice += fruitQuantity.toDouble() * fruitPrice
    }

    private fun updateBasketPriceText() {
        findViewById<TextView>(R.id.basket_price).also { textView ->
            val basketPriceText = "Total: %.2f €".format(basketPrice)
            textView.text = basketPriceText
        }
    }

    private fun resetDefaultValues() {
        fruitQuantity = 0
        computedFruitPrice = 0.00
        spinner.setSelection(0)
    }

    inner class CustomSeekBarListener : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            updateFruitQuantity(progress)
            updateFruitPrice()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}

        override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        private fun updateFruitQuantity(progress: Int) {
            fruitQuantity = progress
            progressValueTextView.text = fruitQuantity.toString()
        }

        private fun updateFruitPrice() {
            computedFruitPrice = fruitPrice * fruitQuantity
            val priceText = "Precio: %.2f €".format(computedFruitPrice)
            priceValueTextView.text = priceText
        }
    }

    inner class SpinnerSelectorListener(private val viewsAffected : Array<View>,
                                        private val fruits : Array<FruitItems>
    ) : AdapterView.OnItemSelectedListener {
        private var fruitText : String = ""

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            fruitQuantity = 0
            seekBar.progress = 0
            fruitText = parent?.findViewById<TextView>(R.id.text).let { it?.text }.toString()

            currentFruitItem = getFruitItem()

            if (position == 0) {
                setVisibility(viewsAffected, View.INVISIBLE)
                return
            }
            setVisibility(viewsAffected, View.VISIBLE)

            updateFruitPrice(position)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}

        private fun setVisibility(viewsAffected: Array<View>, visibility : Int) {
            for (view in viewsAffected) {
                view.visibility = visibility
            }
        }

        private fun getFruitPrice(position: Int) : Double {
            return fruits[position - 1].price
        }

        private fun updateFruitPrice(position: Int) {
            fruitPrice = getFruitPrice(position)
        }

        private fun getFruitItem() : FruitItems? {
            for (f in fruits){
                if (f.fruit.lowercase() == fruitText.lowercase()){
                    return f
                }
            }

            return null
        }
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