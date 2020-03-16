package com.example.chatapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatapp.R

class ContactsAdapter(private val contex : Context, private val contactItems: List<ContactItem>, private val listener: (ContactItem) -> Unit)
    : RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ViewHolder(
        LayoutInflater.from(contex).inflate(
            R.layout.contacts_list_item,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = contactItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(contactItems[position], listener)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val name = view.findViewById<TextView>(R.id.contacts_list_item_name)
        val phoneNumber = view.findViewById<TextView>(R.id.contacts_list_item_phoneNumber)
        val image = view.findViewById<ImageView>(R.id.contacts_list_item_img)

        fun bindItems(items: ContactItem, listener: (ContactItem) -> Unit){
            name.text = items.name
            phoneNumber.text = items.phoneNumber

            Glide.with(itemView.context).load(items.image).into(image)
            itemView.setOnClickListener{
                listener(items)
            }
        }

    }
}
