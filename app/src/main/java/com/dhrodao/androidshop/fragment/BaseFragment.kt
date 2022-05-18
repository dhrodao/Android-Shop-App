package com.dhrodao.androidshop.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel


abstract class BaseFragment<VM : ViewModel, B : ViewDataBinding>(@LayoutRes val layout: Int) : Fragment() {

    // Accessible, but generic, won't have specific methods/fields, see getSpecificBinding()
    open var binding: B? = null

    open lateinit var viewModel: VM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layout, container, false)
        binding?.lifecycleOwner = viewLifecycleOwner

        //If binding available, return it's root otherwise just inflate normally, binding stays null
        return binding?.root ?: inflater.inflate(layout, container, false)
    }

    // Returns a binding of the specific type (instead of ViewDataBinding),
    // which allows access to non-generic methods and fields, doubt that this is really necessary but might be useful to have
    inline fun <reified specific : ViewDataBinding> getSpecificBinding() = binding as? specific
}