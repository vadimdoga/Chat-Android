package com.example.chatapp.loginActivities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.GeneralFunctions
import com.example.chatapp.R
import kotlinx.android.synthetic.main.activity_login_0.*
import me.relex.circleindicator.CircleIndicator


class LoginActivity0 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_0)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val indicator: CircleIndicator = findViewById(R.id.login_0_indicator)
        GeneralFunctions().positionIndicator(2,0, indicator)

        //add fetch function to identify profile. Firebase

        login_verify_email.setOnClickListener{
            val intent = Intent(this, LoginActivity1::class.java)
            intent.putExtra("profileId", "26644bc6-7149-4772-a8b9-b2ac748dbad2")
            startActivity(intent)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId

        if(id == android.R.id.home){
            this.finish()
        }

        return super.onOptionsItemSelected(item)
    }

}
