package com.example.chatapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val fileName:String = getExternalFilesDir(null)?.absolutePath + "/audioFile.wav"
        val audio = AudioRecorder(fileName, applicationContext)

        button_send_req.setOnClickListener{
            val profileId = "26644bc6-7149-4772-a8b9-b2ac748dbad2"
            val body = audio.getBinary()

            FetchFunctions().identifyEnrollment(body, profileId)
        }


        button_start_recording.setOnClickListener{

        }

        button_stop_recording.setOnClickListener{

        }
    }

}
