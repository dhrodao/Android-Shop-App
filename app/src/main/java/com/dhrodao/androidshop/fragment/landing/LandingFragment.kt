package com.dhrodao.androidshop.fragment.landing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.dhrodao.androidshop.dao.AppDatabase
import com.dhrodao.androidshop.dao.ItemViewModelFactory
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

        // Room
        val application = requireNotNull(this.activity).application //construye o toma referencia de DB
        val dao = AppDatabase.getInstance(application).itemDao
        val viewModelFactory = ItemViewModelFactory(dao) //get ViewModel con DAO
        viewModel = ViewModelProvider(
            requireActivity(), viewModelFactory
        )[MainViewModel::class.java]

        // Set username text
        binding!!.usernameText.text = viewModel!!.getUserName()

        return binding!!.root
    }
}