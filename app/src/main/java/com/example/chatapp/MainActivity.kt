package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun toLogin(){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        fun toRegister(){
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        fun toMenu(){
            val intent = Intent(this, MenuActivity::class.java)
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
