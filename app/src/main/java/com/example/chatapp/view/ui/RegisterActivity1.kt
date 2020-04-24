package com.example.chatapp.view.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
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
import com.example.chatapp.model.remote.ApiChatImpl
import com.example.chatapp.viewmodel.RegisterViewModel1
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_register_1.*
import me.relex.circleindicator.CircleIndicator


class RegisterActivity1 : AppCompatActivity() {
    private lateinit var registerViewModel1: RegisterViewModel1
    private val phrList = arrayListOf<String>()
    private var enrollmentStatus : Boolean = false
    private val fileName:String = getExternalFilesDir(null)?.absolutePath + "/audioFile.wav"
    private val audio =
        AudioRecorder(fileName, applicationContext)
    private var azureId : String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_1)

        //configure circle indicator
        val indicator: CircleIndicator = findViewById(R.id.register_1_indicator)
        GeneralFunctions()
            .positionIndicator(2,1, indicator)

        //get azure id from intent
        azureId = if (savedInstanceState == null) {
            val extras = getIntent().extras
            extras?.getString("azureId")
        } else {
            savedInstanceState.getSerializable("azureId") as String?
        }

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        //configure spinner with phrases
        val spinnerAdapter = ArrayAdapter(
            applicationContext,
            android.R.layout.simple_spinner_item,
            phrList
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object: OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val phrase = parent?.getItemAtPosition(position).toString()
                register_textPhrase.text = "Phrase: $phrase"
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        //observe view model
        registerViewModel1 = ViewModelProvider(this).get(RegisterViewModel1::class.java)
        registerViewModel1.phraseList.observe(this, Observer {
            it.forEach{
                    phrase -> phrList.add(phrase.phrase)
            }
            spinnerAdapter.notifyDataSetChanged()
        })
        registerViewModel1.enrollmentStatus.observe(this, Observer {
            if(it){
                enrollmentStatus = true
                val t = Toast.makeText(this,  "Decent recording!", Toast.LENGTH_LONG)
                t. show()
            } else {
                enrollmentStatus = false
                val t = Toast.makeText(this,  "Record Again!", Toast.LENGTH_LONG)
                t. show()
            }
        })
        registerViewModel1.createProfileStatus.observe(this, Observer {
            val profileStatus = it
            if(profileStatus){
                val intent = Intent(this, StartActivity::class.java)
                startActivity(intent)
            } else {
                email_input_register.setText("AzureId or email exists!")
            }
        })
        registerViewModel1.getPhrases()

        //configure audio timer
        val countDownTimer = object  : CountDownTimer(30000,1000){
            override fun onFinish() {
                audio.stopRecording()
                register_textViewCount.text = "Wait!"
                val body = audio.getBinary()
                if (azureId != null){
                    registerViewModel1.createVerifyEnrollment(body, azureId!!)
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                val seconds: Long = millisUntilFinished / 1000
                register_textViewCount.text = Math.round(millisUntilFinished * 0.001f).toString() + "s"
                register_progress_bar.progress = seconds.toInt()
            }
        }

        //click listeners
        audio_enrollment_start.setOnClickListener{
            //check if permissions were activated
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

        audio_enrollment_stop.setOnClickListener{
            countDownTimer.cancel()
            audio.stopRecording()
            register_textViewCount.text = "Try Again!"
            val t = Toast.makeText(applicationContext,  "Bad recording!", Toast.LENGTH_LONG)
            t. show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId

        if(id == android.R.id.home){
            this.finish()
        } else if(id == R.id.form_bar_tick){
            if(enrollmentStatus){
                val email = email_input_register.text.toString()
                registerViewModel1.createUser(email, azureId.toString())
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.form_bar, menu)
        return true
    }

}