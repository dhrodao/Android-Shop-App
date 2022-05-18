package com.dhrodao.androidshop.fragment.orders

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhrodao.androidshop.entities.Order
import com.dhrodao.androidshop.main.R
import com.dhrodao.androidshop.main.databinding.FragmentGlobalBasketBinding
import com.dhrodao.androidshop.main.databinding.FragmentOrdersBinding
import com.dhrodao.androidshop.util.CustomOrderRecyclerAdapter
import com.dhrodao.androidshop.util.CustomShopRecyclerAdapter
import com.dhrodao.androidshop.util.CustomSpinnerAdapter
import com.dhrodao.androidshop.viewmodel.MainViewModel

class OrdersFragment : Fragment() {

    private lateinit var binding: FragmentOrdersBinding

    private lateinit var customOrderRecyclerAdapter : CustomOrderRecyclerAdapter

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrdersBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        customOrderRecyclerAdapter = CustomOrderRecyclerAdapter()

        viewModel.orders.observe(requireActivity(), Observer {
            customOrderRecyclerAdapter.clearItems()
            it.forEach { item ->
                if (!customOrderRecyclerAdapter.isItemInList(item)) {
                    Log.d("Order:", "Item: ${item.orderedItems}")
                    customOrderRecyclerAdapter.addItem(item)
                    customOrderRecyclerAdapter.notifyItemChanged(
                        customOrderRecyclerAdapter.itemCount - 1
                    )
                }
            }
        })

        setupBasketLayout()
    }

    private fun setupBasketLayout() {
        binding.orderContainer.apply { // RecyclerView
            adapter = customOrderRecyclerAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}