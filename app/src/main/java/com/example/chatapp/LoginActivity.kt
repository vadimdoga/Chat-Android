package com.example.chatapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.squti.androidwaverecorder.WaveRecorder
import kotlinx.android.synthetic.main.activity_login.*



class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val subKey = "56cf040fd80a4687b344077f7566bd83"
        val accKey = "f71b7467-aa6e-42f7-8fd1-8c4d08da9570"
        var waveRecorder: WaveRecorder? = null

        button_start_recording.setOnClickListener{
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&  ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(this, permissions,0)
            } else {
                val filePath:String = getExternalFilesDir(null)?.absolutePath + "/audioFile.wav"
                waveRecorder = WaveRecorder(filePath)
                waveRecorder?.startRecording()
                val t = Toast.makeText(applicationContext,  "Started", Toast.LENGTH_LONG)
                t. show()
            }
        }

        button_stop_recording.setOnClickListener{
            waveRecorder?.stopRecording()
            val t = Toast.makeText(applicationContext, "Stopped", Toast.LENGTH_LONG)
            t. show()
        }
    }
}
