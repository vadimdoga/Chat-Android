package com.example.chatapp.loginActivities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.GeneralFunctions
import com.example.chatapp.R
import com.example.chatapp.registerActivities.RegisterActivity0
import com.example.chatapp.registerActivities.RegisterActivity1
import com.example.chatapp.requests.ChatFetchFunctions
import kotlinx.android.synthetic.main.activity_login_0.*
import me.relex.circleindicator.CircleIndicator


class LoginActivity0 : AppCompatActivity() {
    companion object{
        private lateinit var cntLogin: Context

        fun addCnt(cntNew: Context){
            cntLogin = cntNew
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_0)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        addCnt(this)

        val indicator: CircleIndicator = findViewById(R.id.login_0_indicator)
        GeneralFunctions().positionIndicator(2, 0, indicator)

        //add fetch function to identify profile. Firebase

        login_verify_email.setOnClickListener {
            val email = email_input_login.text.toString()
            ChatFetchFunctions().getUser(email)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId

        if(id == android.R.id.home){
            this.finish()
        }

        return super.onOptionsItemSelected(item)
    }

    fun accessLogin1(id: String){
        val localIntent = Intent(cntLogin, LoginActivity1::class.java)
        localIntent.putExtra("azureId", id)
        cntLogin.startActivity(localIntent)
    }

}
