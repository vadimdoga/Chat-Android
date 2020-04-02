package com.example.chatapp.loginActivities

import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.AudioRecorder
import com.example.chatapp.GeneralFunctions
import com.example.chatapp.R
import com.example.chatapp.registerActivities.RegisterActivity1
import com.example.chatapp.requests.FetchFunctions
import kotlinx.android.synthetic.main.activity_login_1.*
import me.relex.circleindicator.CircleIndicator

class LoginActivity1: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_1)

        val indicator: CircleIndicator = findViewById(R.id.login_1_indicator)
        GeneralFunctions().positionIndicator(2,1, indicator)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val fileName:String = getExternalFilesDir(null)?.absolutePath + "/audioFile.wav"
        val audio = AudioRecorder(fileName, applicationContext)

        val spinnerAdapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_spinner_item,
            RegisterActivity1.phrList
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_login.adapter = spinnerAdapter
        spinner_login.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val phrase = parent?.getItemAtPosition(position).toString()
                login_textPhrase.text = "Phrase: $phrase"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        FetchFunctions().getPhrases(spinnerAdapter)


        val countDownTimer = object  : CountDownTimer(30000,1000){
            override fun onFinish() {
                audio.stopRecording()
                val body = audio.getBinary()
                val profileId = intent.getStringExtra("profileId")
                if (profileId != null){
                    val t = Toast.makeText(applicationContext,  "Decent recording!", Toast.LENGTH_LONG)
                    t. show()
                    FetchFunctions().identifyEnrollment(body, profileId)
                    startActivity(intent)
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                val seconds: Long = millisUntilFinished / 1000
                login_textViewCount.text = Math.round(millisUntilFinished * 0.001f).toString() + "s"
                login_progress_bar.progress = seconds.toInt()
            }
        }

        login_start_recording.setOnClickListener{
            if (!GeneralFunctions().checkPermission()) {
                val t = Toast.makeText(applicationContext,  "No Permission!", Toast.LENGTH_LONG)
                t. show()
            } else {
//                audio.startRecording()
                countDownTimer.start()
            }
        }

        login_stop_recording.setOnClickListener{
            audio.stopRecording()
            val t = Toast.makeText(applicationContext,  "Bad recording!", Toast.LENGTH_LONG)
            t. show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId

        if(id == android.R.id.home){
            this.finish()
        } else if(id == R.id.form_bar_tick){
            Toast.makeText(applicationContext,"save", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.form_bar, menu)
        return true
    }
}