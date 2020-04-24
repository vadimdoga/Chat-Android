package com.example.chatapp.view.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.utils.GeneralFunctions
import com.example.chatapp.R
import com.example.chatapp.model.remote.ApiAzureImpl
import com.example.chatapp.viewmodel.RegisterViewModel0
import kotlinx.android.synthetic.main.activity_register_0.*
import me.relex.circleindicator.CircleIndicator


class RegisterActivity0 : AppCompatActivity() {
    private lateinit var registerViewModel0: RegisterViewModel0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_0)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        //observe view model
        registerViewModel0 = ViewModelProvider(this).get(RegisterViewModel0::class.java)
        registerViewModel0.azureId.observe(this, Observer {
            val intent = Intent(this, RegisterActivity1::class.java)
            intent.putExtra("azureId", it)
            startActivity(intent)
        })

        //configure circle indicator
        val indicator: CircleIndicator = findViewById(R.id.register_0_indicator)
        GeneralFunctions()
            .positionIndicator(2,0, indicator)

        //click listeners
        register_createProfile_btn.setOnClickListener{
            registerViewModel0.createProfile()
        }

        next_page_btn.setOnClickListener{
            val azureId = register_profileId.text.toString()
            if(azureId != ""){
                val intent = Intent(this, RegisterActivity1::class.java)
                intent.putExtra("azureId", azureId)
                startActivity(intent)
            }
        }

    }

    //configure navbar btns
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId

        if(id == android.R.id.home){
            this.finish()
        }

        return super.onOptionsItemSelected(item)
    }
}