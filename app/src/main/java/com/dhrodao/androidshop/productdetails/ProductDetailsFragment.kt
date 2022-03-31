package com.dhrodao.androidshop.productdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.dhrodao.androidshop.fruitshop.viewmodel.FruitShopViewModel
import com.dhrodao.androidshop.main.databinding.FragmentProductDetailsBinding
import com.dhrodao.androidshop.util.BasketItem

class ProductDetailsFragment : Fragment() {
    private lateinit var binding : FragmentProductDetailsBinding
    private lateinit var fruitShopViewModel: FruitShopViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)

        fruitShopViewModel = ViewModelProvider(requireActivity())[FruitShopViewModel::class.java]
        showProductDetails(fruitShopViewModel.getFruitItemSelected())

        return binding.root
    }

    private fun showProductDetails(basketItem: BasketItem) {
        val iconDrawable = ResourcesCompat.getDrawable(requireActivity().resources, basketItem.icon, null)
        binding.productIcon.setImageDrawable(iconDrawable)
        binding.productName.text = basketItem.item

        val precio = "Precio ${basketItem.pricePerItem} €"
        binding.productPrice.text = precio
    }
}