package com.example.chatapp.view.ui.form

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.utils.DatePicker
import com.example.chatapp.R
import com.example.chatapp.utils.GeneralFunctions
import com.example.chatapp.viewmodel.form.ProfileFormViewModel
import kotlinx.android.synthetic.main.edit_profile_form.*


class ProfileFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile_form)
        
        val actionBar = supportActionBar

        actionBar?.setDisplayHomeAsUpEnabled(true)

        edit_profile_date.setOnClickListener{
            val newFragment =
                DatePicker(
                    edit_profile_date
                )
            newFragment.show(supportFragmentManager, "datePicker")
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
            ProfileFormViewModel().postProfile(json)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.form_bar, menu)
        return true
    }

}
