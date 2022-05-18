package com.dhrodao.androidshop.util

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dhrodao.androidshop.fragment.register.RegisterTabFragment
import com.dhrodao.androidshop.fragment.login.LoginTabFragment

class LoginCollectionAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    private val numberOfFragments = 2

    override fun getItemCount(): Int {
        return numberOfFragments
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LoginTabFragment()
            1 -> RegisterTabFragment()
            else -> LoginTabFragment()
        }
    }
}