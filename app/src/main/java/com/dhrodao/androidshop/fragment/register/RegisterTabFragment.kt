package com.dhrodao.androidshop.fragment.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.dhrodao.androidshop.dao.AppDatabase
import com.dhrodao.androidshop.dao.ItemViewModelFactory
import com.dhrodao.androidshop.entities.User
import com.dhrodao.androidshop.main.R
import com.dhrodao.androidshop.viewmodel.MainViewModel

class RegisterTabFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application //construye o toma referencia de DB
        val itemDao = AppDatabase.getInstance(application).itemDao
        val orderDao = AppDatabase.getInstance(application).orderDao
        val userDao = AppDatabase.getInstance(application).userDao
        val viewModelFactory = ItemViewModelFactory(application, itemDao, orderDao, userDao) //get ViewModel con DAO
        val mainViewModel = ViewModelProvider(
            requireActivity(), viewModelFactory
        )[MainViewModel::class.java]

        val view = inflater.inflate(R.layout.fragment_register_tab, container, false)

        view.findViewById<Button>(R.id.register_button).setOnClickListener {
            val fistName = view.findViewById<EditText>(R.id.name_field).text.toString()
            val secondName = view.findViewById<EditText>(R.id.surname_field).text.toString()
            val userName = view.findViewById<EditText>(R.id.username_field).text.toString()
            val password = view.findViewById<EditText>(R.id.password_field).text.toString()
            val user = User(
                0,
                userName,
                password,
                fistName,
                secondName
            )

            mainViewModel.register(requireContext(), user)
        }

        return view
    }
}