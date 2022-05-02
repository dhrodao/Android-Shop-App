package com.dhrodao.androidshop.fragment.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dhrodao.androidshop.dao.AppDatabase
import com.dhrodao.androidshop.dao.ItemViewModelFactory
import com.dhrodao.androidshop.main.R
import com.dhrodao.androidshop.viewmodel.MainViewModel

class LoginTabFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application //construye o toma referencia de DB
        val dao = AppDatabase.getInstance(application).itemDao //idem
        val viewModelFactory = ItemViewModelFactory(dao) //get ViewModel con DAO
        viewModel = ViewModelProvider(
            requireActivity(), viewModelFactory)[MainViewModel::class.java]

        val view = inflater.inflate(R.layout.fragment_login_tab, container, false)

        view.findViewById<Button>(R.id.sign_in_button).setOnClickListener {
            val username = view.findViewById<TextView>(R.id.username_field).text.toString()
            viewModel.setUserName(username)

            findNavController().navigate(R.id.action_loginFragment_to_landingFragment)
        }

        return view
    }
}