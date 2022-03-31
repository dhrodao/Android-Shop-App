package com.dhrodao.androidshop.inbox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhrodao.androidshop.entities.Message
import com.dhrodao.androidshop.entities.User
import com.dhrodao.androidshop.main.R
import com.dhrodao.androidshop.main.databinding.FragmentSentMessagesBinding
import com.dhrodao.androidshop.main.viewmodel.MainViewModel
import com.dhrodao.androidshop.util.CustomChatRecyclerAdapter

class InboxFragment : Fragment() {
    private lateinit var binding: FragmentSentMessagesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSentMessagesBinding.inflate(inflater, container, false)

        val messageList: ArrayList<Message> = ArrayList()
        messageList.add(Message("Oferta", User("Oferta de productos!"), "9:00"))
        messageList.add(Message("Oferta", User("Oferta de productos!"), "10:00"))

        val username = ViewModelProvider(requireActivity())[MainViewModel::class.java].getUserName()

        val recyclerView = binding.chatRecyclerView
        val chatReciclerAdapter = CustomChatRecyclerAdapter(username, messageList)

        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = chatReciclerAdapter

        return binding.root
    }
}