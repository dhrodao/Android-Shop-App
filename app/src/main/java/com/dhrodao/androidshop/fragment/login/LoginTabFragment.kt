package com.dhrodao.androidshop.fragment.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dhrodao.androidshop.dao.AppDatabase
import com.dhrodao.androidshop.dao.ItemViewModelFactory
import com.dhrodao.androidshop.main.R
import com.dhrodao.androidshop.viewmodel.MainViewModel

class LoginTabFragment : Fragment() {
    private lateinit var viewModel: MainViewModel

    var username: String = ""
    var password: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application //construye o toma referencia de DB
        val itemDao = AppDatabase.getInstance(application).itemDao
        val orderDao = AppDatabase.getInstance(application).orderDao
        val userDao = AppDatabase.getInstance(application).userDao
        val viewModelFactory = ItemViewModelFactory(application, itemDao, orderDao, userDao) //get ViewModel con DAO
        viewModel = ViewModelProvider(
            requireActivity(), viewModelFactory
        )[MainViewModel::class.java]

        val view = inflater.inflate(R.layout.fragment_login_tab, container, false)

        viewModel.isGoodLogin.observe(viewLifecycleOwner, Observer {
            if (it) {
                viewModel.setUserName(username)
                findNavController().navigate(R.id.action_loginFragment_to_landingFragment)
            }
        })

        view.findViewById<Button>(R.id.sign_in_button).setOnClickListener {
            username = view.findViewById<TextView>(R.id.username_field).text.toString()
            password = view.findViewById<TextView>(R.id.password_field).text.toString()

            isGoodLogin(username, password)
        }

        return view
    }

    private fun isGoodLogin(username: String, password: String) {
        viewModel.isGoodLogin(username, password)
    }
}