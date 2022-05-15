package com.dhrodao.androidshop.fragment.sentmessages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhrodao.androidshop.entities.Message
import com.dhrodao.androidshop.entities.User
import com.dhrodao.androidshop.main.databinding.FragmentSentMessagesBinding
import com.dhrodao.androidshop.viewmodel.MainViewModel
import com.dhrodao.androidshop.util.CustomChatRecyclerAdapter

class SentMessagesFragment : Fragment() {
    private lateinit var binding : FragmentSentMessagesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSentMessagesBinding.inflate(inflater, container, false)

        val messageList: ArrayList<Message> = ArrayList()
        messageList.add(Message("Hello", User(0, "dhrodao", "prueba", "prueba", "prueba"), "9:00"))
        messageList.add(Message("Hello", User(0, "dhrodao", "prueba", "prueba", "prueba"), "10:00"))

        val username = ViewModelProvider(requireActivity())[MainViewModel::class.java].getUserName()

        val recyclerView = binding.chatRecyclerView
        val chatReciclerAdapter = CustomChatRecyclerAdapter(username, messageList)

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = chatReciclerAdapter

        return binding.root
    }
}