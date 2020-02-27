package com.example.chatapp.forms

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.fragments.DatePickerFragment
import com.example.chatapp.R
import kotlinx.android.synthetic.main.edit_profile_form.*


class EditProfileForm : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile_form)
        
        val actionBar = supportActionBar

        actionBar?.setDisplayHomeAsUpEnabled(true)

        edit_profile_date.setOnClickListener{
            val newFragment =
                DatePickerFragment(
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
            Toast.makeText(applicationContext,"save",Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.form_bar, menu)
        return true
    }

}
