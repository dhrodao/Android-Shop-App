package com.dhrodao.androidfruitselector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatSpinner
import kotlin.properties.Delegates

open class MainActivity : AppCompatActivity() {
    private lateinit var quantityLayout : View
    private lateinit var priceLayout : View

    private lateinit var spinnerSelectorListener : SpinnerSelectorListener
    private lateinit var spinnerEventsListener : OnSpinnerEventsListenerImpl

    protected var fruitPrice : Int = 0
    protected var fruitQuantity : Int = 0

    private lateinit var seekBar : SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        quantityLayout = findViewById(R.id.quantity_layout)
        priceLayout = findViewById(R.id.price_layout)

        val customAdapter = CustomSpinnerAdapter(this, 0, FruitItems.values())
        findViewById<CustomSpinner>(R.id.fruit_spinner).apply { // Spinner
            adapter = customAdapter
            onItemSelectedListener = run {
                spinnerSelectorListener = SpinnerSelectorListener(
                    arrayOf(quantityLayout, priceLayout),
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
            priceView.text = run {fruitPrice * fruitQuantity}.toString()
        }
    }

    inner class SpinnerSelectorListener(private val viewsAffected : Array<View>,
                                        private val fruits : Array<FruitItems>
    ) : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            fruitQuantity = 0
            seekBar.progress = 0

            if (position == 0) {
                setVisibility(viewsAffected, View.INVISIBLE)
                return
            }
            setVisibility(viewsAffected, View.VISIBLE)

            updateFruitPrice(position)

            val fruitText : CharSequence? = parent?.findViewById<TextView>(R.id.text).let { it?.text }
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