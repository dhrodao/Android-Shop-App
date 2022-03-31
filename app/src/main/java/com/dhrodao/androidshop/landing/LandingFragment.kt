package com.dhrodao.androidshop.landing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dhrodao.androidshop.viewmodel.MainViewModel
import com.dhrodao.androidshop.main.databinding.FragmentLandingBinding

class LandingFragment : Fragment() {
    private var viewModel: MainViewModel? = null
    private var binding : FragmentLandingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLandingBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        // Set username text
        binding!!.usernameText.text = viewModel!!.getUserName()

        return binding!!.root
    }
}