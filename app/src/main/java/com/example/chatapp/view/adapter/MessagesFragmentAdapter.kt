package com.example.chatapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatapp.R
import com.example.chatapp.view.ui.fragment.ChatItem

class MessagesFragmentAdapter(private val contex : Context, private val chatItems: List<ChatItem>, private val listener: (ChatItem) -> Unit)
    : RecyclerView.Adapter<MessagesFragmentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            =
        ViewHolder(
            LayoutInflater.from(contex).inflate(
                R.layout.msg_list_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = chatItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(chatItems[position], listener)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val name = view.findViewById<TextView>(R.id.msg_list_item_name)
        val shortMsg = view.findViewById<TextView>(R.id.msg_list_item_shortMsg)
        val image = view.findViewById<ImageView>(R.id.msg_list_item_img)
        val time = view.findViewById<TextView>(R.id.msg_list_item_time)

        fun bindItems(items: ChatItem, listener: (ChatItem) -> Unit){
            name.text = items.name
            shortMsg.text = items.shortMsg
            time.text = items.time

            Glide.with(itemView.context).load(items.image).into(image)
            itemView.setOnClickListener{
                listener(items)
            }
        }

        }
    }

