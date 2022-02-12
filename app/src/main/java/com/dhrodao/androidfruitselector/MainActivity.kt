package com.dhrodao.androidfruitselector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatSpinner

open class MainActivity : AppCompatActivity() {
    private lateinit var inflater : LayoutInflater

    private lateinit var mainLayout : ViewGroup
    private lateinit var quantityLayout : ViewGroup
    private lateinit var priceLayout : ViewGroup
    private lateinit var basketLayout : ViewGroup

    protected var currentFruitItem : FruitItems? = null

    private lateinit var spinnerSelectorListener : SpinnerSelectorListener
    private lateinit var spinnerEventsListener : OnSpinnerEventsListenerImpl

    protected var fruitText : String = ""
    protected var fruitPrice : Int = 0
    protected var computedFruitPrice : Int = 0
    protected var fruitQuantity : Int = 0
    private var basketPrice : Int = 0

    private lateinit var seekBar : SeekBar
    private lateinit var addToBasketButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inflater = LayoutInflater.from(this)

        mainLayout = findViewById(R.id.mainLayout)
        quantityLayout = findViewById(R.id.quantity_layout)
        priceLayout = findViewById(R.id.price_layout)
        basketLayout = findViewById(R.id.basket_container)

        addToBasketButton = findViewById(R.id.add_basket_button)

        val customAdapter = CustomSpinnerAdapter(this, 0, FruitItems.values())
        findViewById<CustomSpinner>(R.id.fruit_spinner).apply { // Spinner
            adapter = customAdapter
            onItemSelectedListener = run {
                spinnerSelectorListener = SpinnerSelectorListener(
                    arrayOf(quantityLayout, priceLayout, addToBasketButton),
                    FruitItems.values()
                )
                spinnerSelectorListener
            }
            listener = run {
                spinnerEventsListener = OnSpinnerEventsListenerImpl()
                spinnerEventsListener
            }
        }

        val progressValueText = findViewById<TextView>(R.id.quantity_display)
        val priceValueText = findViewById<TextView>(R.id.final_price)
        seekBar = findViewById<SeekBar>(R.id.quantity_seekbar).apply { // SeekBar
            setOnSeekBarChangeListener(CustomSeekBarListener(progressValueText, priceValueText))
        }

        addToBasketButton.setOnClickListener {
            if (fruitQuantity > 0) {
                val basketView = inflater.inflate(R.layout.item_fruit_basket, basketLayout, false)
                setBasketView(basketView, currentFruitItem)
                updateBasketPrice()
                updateBasketPriceText()
            }
        }
    }

    private fun setBasketView(basketView : View, item : FruitItems?) {
        val icon = basketView.findViewById<ImageView>(R.id.image)
        val fruit = basketView.findViewById<TextView>(R.id.text)
        val quantity = basketView.findViewById<TextView>(R.id.fruit_quantity)
        val quantityText = "x$fruitQuantity"

        item?.icon?.let { icon?.setImageResource(it) }
        fruit?.text = item?.fruit
        quantity?.text = quantityText

        basketLayout.addView(basketView)
    }

    private fun updateBasketPrice() {
        basketPrice += fruitQuantity * fruitPrice
    }

    private fun updateBasketPriceText() {
        findViewById<TextView>(R.id.basket_price).also { textView ->
            val basketPriceText = "Total: $basketPrice €"
            textView.text = basketPriceText
        }
    }

    inner class CustomSeekBarListener(private val quantityView: TextView,
                                private val priceView: TextView,
    ) : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            fruitQuantity = progress
            quantityView.text = fruitQuantity.toString() // Update amount text

            updateFruitPriceText()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}

        override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        private fun updateFruitPriceText() {
            computedFruitPrice = fruitPrice * fruitQuantity
            val priceText = "Precio: $computedFruitPrice €"
            priceView.text = priceText
        }
    }

    inner class SpinnerSelectorListener(private val viewsAffected : Array<View>,
                                        private val fruits : Array<FruitItems>
    ) : AdapterView.OnItemSelectedListener {
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

            Toast.makeText(view?.context, "Selected: $fruitText", Toast.LENGTH_SHORT).show()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}

        private fun setVisibility(viewsAffected: Array<View>, visibility : Int) {
            for (view in viewsAffected) {
                view.visibility = visibility
            }
        }

        private fun getFruitPrice(position: Int) : Int {
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