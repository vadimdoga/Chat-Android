package com.example.chatapp.registerActivities

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
import com.example.chatapp.AudioRecorder
import com.example.chatapp.GeneralFunctions
import com.example.chatapp.MainActivity
import com.example.chatapp.R
import com.example.chatapp.requests.AzureFetchFunctions
import com.example.chatapp.requests.ChatFetchFunctions
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_login_0.*
import kotlinx.android.synthetic.main.activity_register_1.*
import me.relex.circleindicator.CircleIndicator
import org.w3c.dom.Text


class RegisterActivity1 : AppCompatActivity() {

    companion object {
        val phrList = arrayListOf<String>()
        private lateinit var globalAzureId: String
        private lateinit var cntRegister: Context
        private lateinit var viewRegister: TextInputEditText
        private var recordingStatus: Boolean = false

        fun addToArray(el: String){
            if (!phrList.contains(el)){
                phrList.add(el)
            }
        }

        fun setView(v: TextInputEditText){
            viewRegister = v
        }

        fun setCnt(newCnt: Context){
            cntRegister = newCnt
        }

        fun setAzureId(id: String){
            globalAzureId = id
        }

        fun setStatus(st: Boolean){
            recordingStatus = st
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_1)

        val indicator: CircleIndicator = findViewById(R.id.register_1_indicator)
        GeneralFunctions().positionIndicator(2,1, indicator)

        setCnt(this)
        setView(email_input_register)

        val azureId: String?
        azureId = if (savedInstanceState == null) {
            val extras = getIntent().extras
            extras?.getString("azureId")
        } else {
            savedInstanceState.getSerializable("azureId") as String?
        }

        if (azureId != null) {
            setAzureId(azureId)
        }

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

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

        AzureFetchFunctions().getPhrases(spinnerAdapter)

        val fileName:String = getExternalFilesDir(null)?.absolutePath + "/audioFile.wav"
        val audio = AudioRecorder(fileName, applicationContext)

        val countDownTimer = object  : CountDownTimer(30000,1000){
            override fun onFinish() {
                audio.stopRecording()
                register_textViewCount.text = "Wait!"
                val body = audio.getBinary()
                if (azureId != null){
                    AzureFetchFunctions().createVerifyEnrollment(body, azureId)
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                val seconds: Long = millisUntilFinished / 1000
                register_textViewCount.text = Math.round(millisUntilFinished * 0.001f).toString() + "s"
                register_progress_bar.progress = seconds.toInt()
            }
        }

        audio_enrollment_start.setOnClickListener{
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

    fun verifyEnrollment(status: Boolean){
        if(status){
            setStatus(true)
            val t = Toast.makeText(cntRegister,  "Decent recording!", Toast.LENGTH_LONG)
            t. show()
        } else {
            setStatus(false)
            val t = Toast.makeText(cntRegister,  "Record Again!", Toast.LENGTH_LONG)
            t. show()
        }
    }

    fun profileExists(status: Boolean){
        if(status){
            val intent = Intent(cntRegister, MainActivity::class.java)
            cntRegister.startActivity(intent)
        } else {
            viewRegister.setText("AzureId or email exists!")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId

        if(id == android.R.id.home){
            this.finish()
        } else if(id == R.id.form_bar_tick){
            if(recordingStatus){
                val email = email_input_register.text.toString()
                ChatFetchFunctions().createUser(email, globalAzureId)
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