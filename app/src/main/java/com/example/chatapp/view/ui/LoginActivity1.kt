package com.example.chatapp.view.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.chatapp.utils.AudioRecorder
import com.example.chatapp.utils.GeneralFunctions
import com.example.chatapp.R
import com.example.chatapp.model.remote.ApiAzureImpl
import com.example.chatapp.viewmodel.LoginViewModel1
import kotlinx.android.synthetic.main.activity_login_1.*
import me.relex.circleindicator.CircleIndicator

class LoginActivity1: AppCompatActivity(){
    private lateinit var loginViewModel1: LoginViewModel1
    private val phrList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_1)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        //set circle indicator to current position
        val indicator: CircleIndicator = findViewById(R.id.login_1_indicator)
        GeneralFunctions()
            .positionIndicator(2,1, indicator)

        //extract data from intent
        val azureId: String?
        azureId = if (savedInstanceState == null) {
            val extras = getIntent().extras
            extras?.getString("azureId")
        } else {
            savedInstanceState.getSerializable("azureId") as String?
        }

        //configure audio recorder
        val fileName:String = getExternalFilesDir(null)?.absolutePath + "/audioFile.wav"
        val audio =
            AudioRecorder(fileName, applicationContext)

        //spinner with phrases
        val spinnerAdapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_spinner_item,
            phrList
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

        //observe view model
        loginViewModel1 = ViewModelProvider(this).get(LoginViewModel1::class.java)
        loginViewModel1.phraseList.observe(this, Observer {
            it.forEach{
                phrase -> phrList.add(phrase.phrase)
            }
            spinnerAdapter.notifyDataSetChanged()
        })
        loginViewModel1.enrollmentStatus.observe(this, Observer {
            if(it == "High"){
                val intent = Intent(this, ChatMenuActivity::class.java)
                startActivity(intent)
            } else {
                val t = Toast.makeText(this,  "Voice Negative!", Toast.LENGTH_LONG)
                t. show()
            }
        })

        loginViewModel1.getPhrases()

        //set timer for audio recording
        val countDownTimer = object  : CountDownTimer(10000,1000){
            override fun onFinish() {
                audio.stopRecording()
                login_textViewCount.text = "Wait!"
                val body = audio.getBinary()
                if (azureId != null){
                    loginViewModel1.identifyEnrollment(azureId, body)
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                val seconds: Long = millisUntilFinished / 1000
                login_textViewCount.text = Math.round(millisUntilFinished * 0.001f).toString() + "s"
                login_progress_bar.progress = seconds.toInt()
            }
        }

        //click listeners
        login_start_recording.setOnClickListener{
            //check for permissions of microphone and save file
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED) {
                val permissions = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(this, permissions,0)
                val t = Toast.makeText(applicationContext,  "No Permission!", Toast.LENGTH_LONG)
                t. show()
            } else {
                audio.startRecording()
                countDownTimer.start()
            }
        }

        login_stop_recording.setOnClickListener{
            audio.stopRecording()
            countDownTimer.cancel()
            login_textViewCount.text = "Try Again!"
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