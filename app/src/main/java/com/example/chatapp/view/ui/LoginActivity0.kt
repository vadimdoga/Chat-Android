package com.example.chatapp.view.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.utils.GeneralFunctions
import com.example.chatapp.R
import com.example.chatapp.viewmodel.LoginViewModel0
import kotlinx.android.synthetic.main.activity_login_0.*
import me.relex.circleindicator.CircleIndicator


class LoginActivity0 : AppCompatActivity() {
    private lateinit var loginviewmodel0: LoginViewModel0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_0)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        //configure ui circle indicators
        val indicator: CircleIndicator = findViewById(R.id.login_0_indicator)
        GeneralFunctions()
            .positionIndicator(2, 0, indicator)

        //observe view model
        loginviewmodel0 = ViewModelProvider(this).get(LoginViewModel0::class.java)
        loginviewmodel0.userDetails.observe(this, Observer {
            val id = it
            val intent = Intent(this, LoginActivity1::class.java)
            intent.putExtra("azureId", id)
            startActivity(intent)
        })

        //click listeners
        login_verify_email.setOnClickListener {
            val email = email_input_login.text.toString()
            loginviewmodel0.getUser(email)
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
