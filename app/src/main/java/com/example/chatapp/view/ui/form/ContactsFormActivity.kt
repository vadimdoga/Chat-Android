package com.example.chatapp.view.ui.form

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.R
import com.example.chatapp.utils.GeneralFunctions
import com.example.chatapp.viewmodel.form.ContactsFormViewModel
import kotlinx.android.synthetic.main.contact_form.*

class ContactsFormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_form)

        val actionBar = supportActionBar

        actionBar?.setDisplayHomeAsUpEnabled(true)

        contact_form_img.setOnClickListener{
            //todo: add upload image method
            Toast.makeText(applicationContext,"upload",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId

        if(id == android.R.id.home){
            this.finish()
        }
        if(id == R.id.form_bar_tick){
            //todo: make a final version of this, now just for testing
            val value = arrayListOf<String>()
            val key = arrayListOf<String>()
            value.add("test")
            key.add("test")
            val json = GeneralFunctions().createJsonBody(value, key)
            ContactsFormViewModel().postContact(json)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.form_bar, menu)
        return true
    }



}