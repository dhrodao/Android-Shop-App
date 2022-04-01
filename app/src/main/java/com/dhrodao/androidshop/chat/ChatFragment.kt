package com.dhrodao.androidshop.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhrodao.androidshop.entities.Message
import com.dhrodao.androidshop.entities.User
import com.dhrodao.androidshop.main.databinding.FragmentChatBinding
import com.dhrodao.androidshop.viewmodel.MainViewModel
import com.dhrodao.androidshop.util.CustomChatRecyclerAdapter


class ChatFragment : Fragment() {
    private lateinit var binding : FragmentChatBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChatBinding.inflate(inflater, container, false)

        val messageList: ArrayList<Message> = ArrayList()
        messageList.add(Message("Hello", User("prueba"), "10:00"))
        messageList.add(Message("Hello", User("dhrodao"), "10:00"))

        val username = ViewModelProvider(requireActivity())[MainViewModel::class.java].getUserName()

        val recyclerView = binding.chatRecyclerView
        val chatReciclerAdapter = CustomChatRecyclerAdapter(username, messageList)

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = chatReciclerAdapter

        return binding.root
    }
}