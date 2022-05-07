package com.dhrodao.androidshop.fragment.fruitshop

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dhrodao.androidshop.dao.AppDatabase
import com.dhrodao.androidshop.dao.ItemViewModelFactory
import com.dhrodao.androidshop.fragment.ShopFragment
import com.dhrodao.androidshop.main.R
import com.dhrodao.androidshop.main.databinding.FragmentFruitShopBinding
import com.dhrodao.androidshop.util.CustomSpinnerAdapter
import com.dhrodao.androidshop.viewmodel.MainViewModel

class FruitShopFragment : ShopFragment<FragmentFruitShopBinding>(R.layout.fragment_fruit_shop) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Room
        val application = requireNotNull(this.activity).application //construye o toma referencia de DB
        val itemDao = AppDatabase.getInstance(application).itemDao
        val orderDao = AppDatabase.getInstance(application).orderDao
        val viewModelFactory = ItemViewModelFactory(application, itemDao, orderDao) //get ViewModel con DAO
        val mainViewModel = ViewModelProvider(
            requireActivity(), viewModelFactory
        )[MainViewModel::class.java]

        viewModel = mainViewModel.fruitShopViewModel

        val binding = getSpecificBinding<FragmentFruitShopBinding>()
        binding?.viewModel = viewModel

        affectedUIItems = arrayOf(binding!!.quantityLayout, binding.priceLayout, binding.addBasketButton)

        setup()

        mainViewModel.fruitItems.observe(requireActivity(), Observer {
            it.forEach { item ->
                Log.d("FruitShopFragment", "Item: $item")
                (spinner.adapter as CustomSpinnerAdapter).add(item)
            }
            spinner.setSelection(viewModel.currentSpinnerItem.value!!)
        })
    }
    override fun initComponents() {
        val binding = getSpecificBinding<FragmentFruitShopBinding>()

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
            FruitShopFragmentDirections.actionFruitShopFragmentToProductDetailsFragment(
                viewModel.itemType
            )
        )
    }
}