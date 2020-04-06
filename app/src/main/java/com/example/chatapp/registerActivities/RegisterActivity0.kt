package com.example.chatapp.registerActivities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.GeneralFunctions
import com.example.chatapp.R
import com.example.chatapp.requests.AzureFetchFunctions
import kotlinx.android.synthetic.main.activity_register_0.*
import me.relex.circleindicator.CircleIndicator


class RegisterActivity0 : AppCompatActivity() {
    companion object GlobalCnt{
        private lateinit var cnt: Context

        fun addCnt(cntNew: Context){
            cnt = cntNew
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_0)

        addCnt(this)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val indicator: CircleIndicator = findViewById(R.id.register_0_indicator)
        GeneralFunctions().positionIndicator(2,0, indicator)

        register_createProfile_btn.setOnClickListener{
            AzureFetchFunctions().createProfile()
        }

        next_page_btn.setOnClickListener{
            val azureId = register_profileId.text.toString()
            if(azureId != "")
                accessRegister1(azureId)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId

        if(id == android.R.id.home){
            this.finish()
        }

        return super.onOptionsItemSelected(item)
    }

    fun accessRegister1(id: String){
        val localIntent = Intent(cnt, RegisterActivity1::class.java)
        localIntent.putExtra("azureId", id)
        cnt.startActivity(localIntent)
    }
}