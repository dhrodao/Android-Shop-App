package com.dhrodao.androidshop.fragment.globalbasket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhrodao.androidshop.main.databinding.FragmentGlobalBasketBinding
import com.dhrodao.androidshop.util.CustomShopRecyclerAdapter
import com.dhrodao.androidshop.viewmodel.MainViewModel
import kotlin.math.abs

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

        binding.addBasketButton.setOnClickListener {
            val globalBasket = viewModel.basketItems.value
            if (globalBasket != null && globalBasket.isNotEmpty()) {
                viewModel.purchaseItems()
                customShopRecyclerAdapter.notifyDataSetChanged()
                Toast.makeText(context, "Added to basket", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.basketPrice.observe(viewLifecycleOwner) {
            val totalText = "Total: %.2f €".format(abs(it))
            binding.basketPrice.text = totalText
        }

        setup()
    }

    private fun setup() {
        setupBasketLayout()
    }

    private fun setupBasketLayout() {
        val fragment = this
        binding.basketContainer.apply { // RecyclerView
            customShopRecyclerAdapter =
                CustomShopRecyclerAdapter(fragment, viewModel.basketItems.value!!, null, null)
            adapter = customShopRecyclerAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}