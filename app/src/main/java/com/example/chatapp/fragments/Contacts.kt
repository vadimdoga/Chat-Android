package com.example.chatapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.adapters.ContactsAdapter
import com.example.chatapp.forms.ContactForm
import com.example.chatapp.ContactItem
import com.example.chatapp.R

import kotlinx.android.synthetic.main.fragment_contacts.*


class Contacts : Fragment() {

    private var contactItems: MutableList<ContactItem> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()

        rv_contacts_list.layoutManager = LinearLayoutManager(activity)
        rv_contacts_list.adapter = ContactsAdapter(
            this.requireContext(),
            contactItems
        ) {
            val toast =
                Toast.makeText(activity?.applicationContext, it.phoneNumber, Toast.LENGTH_LONG)
            toast.show()
        }

        contacts_list_item_add.setOnClickListener{
            toContactForm()
        }
    }

    private fun initData(){
        val image = resources.obtainTypedArray(R.array.msg_image)
        val name = resources.getStringArray(R.array.msg_name)
        val phoneNumber = resources.getStringArray(R.array.contacts_phoneNumber)
        contactItems.clear()
        for (i in name.indices){
            contactItems.add(
                ContactItem(
                    name[i],
                    phoneNumber[0],
                    image.getResourceId(0, 0)
                )
            )
        }
    }
    private fun toContactForm(){
        val intent = Intent(this.requireContext(), ContactForm::class.java)
        startActivity(intent)
    }
}
