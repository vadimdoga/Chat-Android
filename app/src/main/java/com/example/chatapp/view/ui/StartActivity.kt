package com.example.chatapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatapp.R
import kotlinx.android.synthetic.main.activity_main.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun toLogin(){
            val intent = Intent(this, LoginActivity0::class.java)
            startActivity(intent)
        }
        fun toRegister(){
            val intent = Intent(this, RegisterActivity0::class.java)
            startActivity(intent)
        }

        login_btn.setOnClickListener{
            toLogin()
        }
        register_btn.setOnClickListener{
            toRegister()
        }

    }
}
