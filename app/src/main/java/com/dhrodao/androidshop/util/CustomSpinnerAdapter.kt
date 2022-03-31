package com.dhrodao.androidshop.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.dhrodao.androidshop.items.BasketItems
import com.dhrodao.androidshop.main.R

class CustomSpinnerAdapter(context : Context,
                           resource: Int, objects: ArrayList<BasketItems>,
) : ArrayAdapter<BasketItems>(context, resource, objects) {
    private val layoutInflater : LayoutInflater = LayoutInflater.from(context)
    private var basketView : View? = null

    // The value shown in the spinner
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var rootView : View = layoutInflater.inflate(R.layout.item_fruit, parent,false)
        basketView = layoutInflater.inflate(R.layout.item_fruit_basket, parent,false)

        getItem(position)?.let { setItemView(rootView, it); setBasketView(it) } ?:
        run { rootView = layoutInflater.inflate(R.layout.select_item, parent, false) }

        return rootView
    }

    // For the dropdown view when the user clicks the spinner
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var rootView = layoutInflater.inflate(R.layout.item_fruit, parent,false)

        getItem(position)?.let { setItemView(rootView, it) } ?:
        run { rootView = layoutInflater.inflate(R.layout.select_item, parent, false) }

        rootView.findViewById<TextView>(R.id.text).setTextColor(Color.WHITE)

        // If not last item in list, draw separator
        if (position < count - 1) {
            rootView.background = generateSeparator()
        }

        return rootView
    }

    // Get an item from the list
    override fun getItem(position: Int): BasketItems? {
        if (position == 0) {  // Show "Selecciona un ítem"
            return null
        }

        return super.getItem(position - 1)
    }

    override fun getCount() = super.getCount() + 1

    private fun setItemView(rootView: View, item : BasketItems) {
        val icon = rootView.findViewById<ImageView>(R.id.image)
        val fruit = rootView.findViewById<TextView>(R.id.text)

        item.icon.let { icon?.setImageResource(it) }
        fruit?.text = item.item
    }

    private fun generateSeparator(
        bgColor : Int = context.getColor(R.color.design_default_color_primary),
        sepColor : Int = Color.BLACK,
        sepWidthPixels : Int = 4
    ) : LayerDrawable {
        val layers = arrayOf(ColorDrawable(sepColor), ColorDrawable(bgColor))
        val layerDrawable = LayerDrawable(layers)

        layerDrawable.setLayerInset(
            1, // Select BackGround index
            0, // Left
            0, // Top
            0, // Right
            sepWidthPixels // Bottom
        )

        return layerDrawable
    }

    private fun setBasketView(item : BasketItems) {
        val icon = basketView?.findViewById<ImageView>(R.id.image)
        val fruit = basketView?.findViewById<TextView>(R.id.text)

        item.icon.let { icon?.setImageResource(it) }
        fruit?.text = item.item
    }

    fun getBasketView() : View? {
        return basketView
    }
}