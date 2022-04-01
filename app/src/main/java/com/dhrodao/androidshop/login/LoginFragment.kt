package com.dhrodao.androidshop.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dhrodao.androidshop.main.MainActivity
import com.dhrodao.androidshop.main.R
import com.dhrodao.androidshop.viewmodel.MainViewModel
import com.google.android.material.appbar.MaterialToolbar

class LoginFragment : Fragment() {
    private lateinit var viewModel : MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        hideDrawerIcon()

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        view.findViewById<Button>(R.id.login_button).setOnClickListener {
            val username = view.findViewById<TextView>(R.id.login_edit_text).text.toString()
            viewModel.setUserName(username)

            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToLandingFragment())
        }
    }

    private fun hideDrawerIcon(){
        val activity = requireActivity() as MainActivity
        val toolbar = activity.findViewById<MaterialToolbar>(R.id.topAppBar)
        toolbar.navigationIcon = null
    }
}