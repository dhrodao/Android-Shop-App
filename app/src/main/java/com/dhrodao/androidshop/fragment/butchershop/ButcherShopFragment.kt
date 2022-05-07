package com.dhrodao.androidshop.fragment.butchershop

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
import com.dhrodao.androidshop.main.databinding.FragmentButcherShopBinding
import com.dhrodao.androidshop.main.databinding.FragmentFishShopBinding
import com.dhrodao.androidshop.util.CustomSpinnerAdapter
import com.dhrodao.androidshop.viewmodel.MainViewModel

class ButcherShopFragment : ShopFragment<FragmentFishShopBinding>(R.layout.fragment_butcher_shop) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java].butcherShopViewModel

        val binding = getSpecificBinding<FragmentButcherShopBinding>()
        binding?.viewModel = viewModel

        affectedUIItems = arrayOf(binding!!.quantityLayout, binding.priceLayout, binding.addBasketButton)

        setup()

        // Room
        val application = requireNotNull(this.activity).application //construye o toma referencia de DB
        val itemDao = AppDatabase.getInstance(application).itemDao
        val orderDao = AppDatabase.getInstance(application).orderDao
        val viewModelFactory = ItemViewModelFactory(application, itemDao, orderDao) //get ViewModel con DAO
        val mainViewModel = ViewModelProvider(
            requireActivity(), viewModelFactory
        )[MainViewModel::class.java]

        mainViewModel.butcherItems.observe(requireActivity(), Observer {
            it.forEach { item ->
                Log.d("ButcherShopFragment", "Item: $item")
                (spinner.adapter as CustomSpinnerAdapter).add(item)
            }
            spinner.setSelection(viewModel.currentSpinnerItem.value!!)
        })
    }
    override fun initComponents() {
        val binding = getSpecificBinding<FragmentButcherShopBinding>()

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
            ButcherShopFragmentDirections.actionButcherShopFragmentToProductDetailsFragment(
                viewModel.itemType
            )
        )
    }
}