package com.dhrodao.androidfruitselector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
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

    private var onRestore : Boolean = false
    private lateinit var restoredListContent : ArrayList<BasketItem>

    private var basketItems : ArrayList<BasketItem> = ArrayList()

    private lateinit var customRecyclerAdapter : CustomRecyclerAdapter // Dynamic list so need to access from methods

    // Needs to inherit from parcelable in order to persist views when screen changes
    data class BasketItem(val fruit: String?, val icon: Int, val quantity: Int) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(fruit)
            parcel.writeInt(icon)
            parcel.writeInt(quantity)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<BasketItem> {
            override fun createFromParcel(parcel: Parcel): BasketItem {
                return BasketItem(parcel)
            }

            override fun newArray(size: Int): Array<BasketItem?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("FruitSelector", "onCreate")

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

    override fun onRestoreInstanceState(outState: Bundle) {
        super.onRestoreInstanceState(outState)
        Log.d("FruitSelector", "onRestoreInstanceState")
        onRestore = true
        outState.let {
            it.getParcelableArrayList<BasketItem>("basketRecyclerViewItems").also { restoredParcelable ->
                if (restoredParcelable != null) {
                    restoredListContent = restoredParcelable
                    for (item in restoredListContent){
                        val index = basketItems.size
                        basketItems.add(item)
                        customRecyclerAdapter.notifyItemInserted(index)
                    }
                }
            }
            it.getInt("currentSpinnerItem").also { selection -> spinner.setSelection(selection) }
            it.getDouble("fruitPrice").also { price -> fruitPrice = price }
            it.getDouble("computedFruitPrice").also { computed -> computedFruitPrice = computed }
            it.getDouble("basketPrice").also { price -> basketPrice = price; updateBasketPriceText() }
            it.getInt("fruitQuantity").also { quantity -> fruitQuantity = quantity }
            it.getInt("currentProgress").also { progress -> seekBar.progress = progress }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelableArrayList("basketRecyclerViewItems", basketItems)
        outState.putInt("currentSpinnerItem", spinner.selectedItemPosition)
        outState.putDouble("fruitPrice", fruitPrice)
        outState.putDouble("computedFruitPrice", computedFruitPrice)
        outState.putDouble("basketPrice", basketPrice)
        outState.putInt("fruitQuantity", fruitQuantity)
        outState.putInt("currentProgress", seekBar.progress)
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
        private var prevPosition : Int? = null

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            resetData(position)

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

        private fun resetData(position: Int) {
            prevPosition?.let { if (it != position) onRestore = false }

            if (!onRestore) {
                fruitQuantity = 0
                seekBar.progress = 0
            }

            prevPosition = position
        }

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