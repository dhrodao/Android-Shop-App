package com.dhrodao.androidshop.globalbasket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhrodao.androidshop.main.databinding.FragmentGlobalBasketBinding
import com.dhrodao.androidshop.util.CustomShopRecyclerAdapter
import com.dhrodao.androidshop.viewmodel.MainViewModel

class GlobalBasketFragment : Fragment() {
    private lateinit var binding: FragmentGlobalBasketBinding

    private lateinit var customShopRecyclerAdapter : CustomShopRecyclerAdapter

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGlobalBasketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        setup()
    }

    private fun setup() {
        val itemPrice = viewModel.basketPrice.value ?: 0.0
        val totalText = "Total: %.2f €".format(itemPrice)
        binding.basketPrice.text = totalText

        setupBasketLayout()
    }

    private fun setupBasketLayout() {
        binding.basketContainer.apply { // RecyclerView
            customShopRecyclerAdapter =
                CustomShopRecyclerAdapter(viewModel.basketItems.value!!)
            adapter = customShopRecyclerAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}