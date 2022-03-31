package com.dhrodao.androidshop.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhrodao.androidshop.entities.Message
import com.dhrodao.androidshop.main.R
import org.w3c.dom.Text

class CustomChatRecyclerAdapter(private val hostUsername: String, private val messages : ArrayList<Message>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVED = 2

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.sent_chat_message, parent, false)
            SentMessageViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.received_chat_message, parent, false)
            ReceivedMessageViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder:  RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        when(holder.itemViewType){
            VIEW_TYPE_MESSAGE_SENT -> (holder as SentMessageViewHolder).bind(message)
            VIEW_TYPE_MESSAGE_RECEIVED -> (holder as ReceivedMessageViewHolder).bind(message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message: Message = messages[position]

        return if (message.sender.username.trim() == hostUsername.trim()) {
            // If the current user is the sender of the message
            VIEW_TYPE_MESSAGE_SENT
        } else {
            // If some other user sent the message
            VIEW_TYPE_MESSAGE_RECEIVED
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    interface MessageViewHolder {
        fun bind(message: Message)
    }

    class ReceivedMessageViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView), MessageViewHolder {
        private val messageText: TextView = itemView.findViewById(R.id.text_gchat_message_other)
        private val usernameText: TextView = itemView.findViewById(R.id.text_gchat_user_other)
        private val timeText: TextView = itemView.findViewById(R.id.text_gchat_timestamp_other)

        override fun bind(message: Message) {
            messageText.text = message.message
            usernameText.text = message.sender.username
            timeText.text = message.createdAt
        }
    }

    class SentMessageViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView), MessageViewHolder {
        private val messageText: TextView = itemView.findViewById(R.id.text_gchat_message_me)
        private val timeText: TextView = itemView.findViewById(R.id.text_gchat_timestamp_me)

        override fun bind(message: Message) {
            messageText.text = message.message
            timeText.text = message.createdAt
        }
    }
}