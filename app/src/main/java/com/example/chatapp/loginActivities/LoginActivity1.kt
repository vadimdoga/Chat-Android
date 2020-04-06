package com.example.chatapp.loginActivities

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
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.chatapp.AudioRecorder
import com.example.chatapp.GeneralFunctions
import com.example.chatapp.MenuActivity
import com.example.chatapp.R
import com.example.chatapp.registerActivities.RegisterActivity1
import com.example.chatapp.requests.AzureFetchFunctions
import com.example.chatapp.requests.Confidence
import kotlinx.android.synthetic.main.activity_login_1.*
import me.relex.circleindicator.CircleIndicator

class LoginActivity1: AppCompatActivity(){
    companion object GlobalCnt{
        private lateinit var cntMenu: Context

        fun addCnt(cntNew: Context){
            cntMenu = cntNew
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_1)

        val indicator: CircleIndicator = findViewById(R.id.login_1_indicator)
        GeneralFunctions().positionIndicator(2,1, indicator)

        addCnt(this)

        val azureId: String?
        azureId = if (savedInstanceState == null) {
            val extras = getIntent().extras
            extras?.getString("azureId")
        } else {
            savedInstanceState.getSerializable("azureId") as String?
        }

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

        AzureFetchFunctions().getPhrases(spinnerAdapter)


        val countDownTimer = object  : CountDownTimer(10000,1000){
            override fun onFinish() {
                audio.stopRecording()
                val body = audio.getBinary()
                if (azureId != null){
                    val t = Toast.makeText(applicationContext,  "Decent recording!", Toast.LENGTH_LONG)
                    t. show()
                    AzureFetchFunctions().identifyEnrollment(body, azureId)
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                val seconds: Long = millisUntilFinished / 1000
                login_textViewCount.text = Math.round(millisUntilFinished * 0.001f).toString() + "s"
                login_progress_bar.progress = seconds.toInt()
            }
        }

        login_start_recording.setOnClickListener{
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
            val t = Toast.makeText(applicationContext,  "Bad recording!", Toast.LENGTH_LONG)
            t. show()
        }
    }

    fun accessMenu(status: String){
        println(status)
        if(status == "High"){
            val localIntent = Intent(cntMenu, MenuActivity::class.java)
            cntMenu.startActivity(localIntent)
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