package com.example.chatapp.registerActivities

import android.content.Intent
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
import com.example.chatapp.AudioRecorder
import com.example.chatapp.GeneralFunctions
import com.example.chatapp.MainActivity
import com.example.chatapp.requests.AzureFetchFunctions
import com.example.chatapp.R
import kotlinx.android.synthetic.main.activity_register_1.*
import me.relex.circleindicator.CircleIndicator

class RegisterActivity1 : AppCompatActivity() {

    companion object {
        val phrList = arrayListOf<String>()

        fun addToArray(el: String){
            if (!phrList.contains(el)){
                phrList.add(el)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_1)

        val intent = Intent(this, MainActivity::class.java)

        val indicator: CircleIndicator = findViewById(R.id.register_1_indicator)
        GeneralFunctions().positionIndicator(2,1, indicator)

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
                val body = audio.getBinary()
                val azureId = intent.getStringExtra("azureId")
                if (azureId != null){
                    val t = Toast.makeText(applicationContext,  "Decent recording!", Toast.LENGTH_LONG)
                    t. show()
                    AzureFetchFunctions().createEnrollment(body, azureId)
                    startActivity(intent)
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                val seconds: Long = millisUntilFinished / 1000
                textViewCount.text = Math.round(millisUntilFinished * 0.001f).toString() + "s"
                register_progress_bar.progress = seconds.toInt()
            }
        }

        audio_enrollment_start.setOnClickListener{
            //todo: checkpermission return null -> refactor
            if (!GeneralFunctions().checkPermission()) {
                val t = Toast.makeText(applicationContext,  "No Permission!", Toast.LENGTH_LONG)
                t. show()
            } else {
                audio.startRecording()
                countDownTimer.start()
            }
        }

        audio_enrollment_stop.setOnClickListener{
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

            Toast.makeText(applicationContext,"save",Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.form_bar, menu)
        return true
    }

}