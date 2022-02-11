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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val customAdapter = CustomSpinnerAdapter(this, 0, FruitItems.values())
        findViewById<CustomSpinner>(R.id.fruit_spinner).apply {
            adapter = customAdapter
            onItemSelectedListener = SpinnerSelectorListener(this@MainActivity)
            listener = OnSpinnerEventsListenerImpl()
        }

        val progressValueText = findViewById<TextView>(R.id.quantity_display)
        findViewById<SeekBar>(R.id.quantity_seekbar).apply {
            setOnSeekBarChangeListener(CustomSeekBarListener(progressValueText))
        }
    }
}

class CustomSeekBarListener(val textView: TextView) : SeekBar.OnSeekBarChangeListener {
    private var progressVal : Int = 0

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        progressVal = progress
        textView.text = progressVal.toString()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}

    override fun onStopTrackingTouch(seekBar: SeekBar?) {}
}

class SpinnerSelectorListener(private val mainView: MainActivity) : AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val quantityLayout = mainView.findViewById<View>(R.id.quantity_layout)
        val priceLayout = mainView.findViewById<View>(R.id.price_layout)

        if (position == 0) {
            quantityLayout.visibility = View.INVISIBLE
            priceLayout.visibility = View.INVISIBLE

            return
        }

        quantityLayout.visibility = View.VISIBLE
        priceLayout.visibility = View.VISIBLE

        val fruitText : CharSequence? = parent?.findViewById<TextView>(R.id.text).let { it?.text }
        Toast.makeText(view?.context, "Selected: $fruitText", Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Log.d("CustomSpinner", "NADA")
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