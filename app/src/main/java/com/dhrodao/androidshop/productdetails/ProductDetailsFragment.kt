package com.dhrodao.androidshop.productdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import com.dhrodao.androidshop.items.ItemTypes
import com.dhrodao.androidshop.main.databinding.FragmentProductDetailsBinding
import com.dhrodao.androidshop.util.BasketItem
import com.dhrodao.androidshop.viewmodel.MainViewModel
import com.dhrodao.androidshop.viewmodel.ShopViewModel

class ProductDetailsFragment : Fragment() {
    private lateinit var binding : FragmentProductDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)

        val mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        val itemType = ProductDetailsFragmentArgs.fromBundle(requireArguments()).itemType
        val viewModel = when(itemType){
            ItemTypes.FRUIT -> mainViewModel.fruitShopViewModel
            else -> {
                mainViewModel.sportsShopViewModel
            }
        }
        showProductDetails(viewModel.getSelectedItem())

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