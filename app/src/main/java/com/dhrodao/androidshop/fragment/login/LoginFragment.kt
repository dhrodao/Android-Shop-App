package com.dhrodao.androidshop.fragment.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.dhrodao.androidshop.dao.AppDatabase
import com.dhrodao.androidshop.dao.ItemViewModelFactory
import com.dhrodao.androidshop.items.ItemTypes
import com.dhrodao.androidshop.main.MainActivity
import com.dhrodao.androidshop.main.R
import com.dhrodao.androidshop.util.LoginCollectionAdapter
import com.dhrodao.androidshop.viewmodel.MainViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class LoginFragment : Fragment() {
    private lateinit var loginCollectionAdapter: LoginCollectionAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideDrawerIcon()

        val application = requireNotNull(this.activity).application //construye o toma referencia de DB
        val itemDao = AppDatabase.getInstance(application).itemDao
        val orderDao = AppDatabase.getInstance(application).orderDao
        val userDao = AppDatabase.getInstance(application).userDao
        val viewModelFactory = ItemViewModelFactory(application, itemDao, orderDao, userDao) //get ViewModel con DAO
        val mainViewModel = ViewModelProvider(
            requireActivity(), viewModelFactory
        )[MainViewModel::class.java]

        mainViewModel.fishItems.observe(requireActivity(), Observer {
            Log.d("LoginFragment", "fishItems: ${it.size}")
        })

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        // Set collection adapter
        loginCollectionAdapter = LoginCollectionAdapter(this)
        viewPager = view.findViewById(R.id.login_fragment_view_pager)
        viewPager.adapter = loginCollectionAdapter

        // Set tab layout mediator
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position){
                0 ->  tab.text = "Login"
                1 -> tab.text = "Register"
                else -> tab.text = "Login"
            }
        }.attach()

        return view
    }

    private fun hideDrawerIcon(){
        val activity = requireActivity() as MainActivity
        val toolbar = activity.findViewById<MaterialToolbar>(R.id.topAppBar)
        toolbar.navigationIcon = null
    }
}