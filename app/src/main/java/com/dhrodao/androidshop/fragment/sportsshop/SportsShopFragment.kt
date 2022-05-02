package com.dhrodao.androidshop.fragment.sportsshop

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dhrodao.androidshop.fragment.ShopFragment
import com.dhrodao.androidshop.main.R
import com.dhrodao.androidshop.main.databinding.FragmentSportsShopBinding
import com.dhrodao.androidshop.viewmodel.MainViewModel

class SportsShopFragment : ShopFragment<FragmentSportsShopBinding>(R.layout.fragment_sports_shop) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java].sportsShopViewModel

        val binding = getSpecificBinding<FragmentSportsShopBinding>()
        binding?.viewModel = viewModel

        affectedUIItems = arrayOf(binding!!.quantityLayout, binding.priceLayout, binding.addBasketButton)

        setup()
    }
    override fun initComponents() {
        val binding = getSpecificBinding<FragmentSportsShopBinding>()

        mainLayout = binding!!.mainLayout
        quantityLayout = binding.quantityLayout
        priceLayout = binding.priceLayout
        basketLayout = binding.basketContainer

        progressValueTextView = binding.quantityDisplay
        priceValueTextView = binding.finalPrice
        totalValueTextView = binding.basketPrice

        addToBasketButton = binding.addBasketButton
        spinner = binding.itemSpinner
        seekBar = binding.quantitySeekbar
    }

    override fun navigateToProductDetails(view: View) {
        val basketItem = viewModel.getBasketItems()[basketLayout.getChildAdapterPosition(view)]
        viewModel.setSelectedItem(basketItem)
        findNavController().navigate(
            SportsShopFragmentDirections.actionSportsShopFragmentToProductDetailsFragment(
                viewModel.itemType
            )
        )
    }
}