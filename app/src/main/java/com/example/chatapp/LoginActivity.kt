package com.example.chatapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.windsekirun.naraeaudiorecorder.NaraeAudioRecorder
import com.github.windsekirun.naraeaudiorecorder.config.AudioRecordConfig
import com.github.windsekirun.naraeaudiorecorder.constants.AudioConstants
import com.github.windsekirun.naraeaudiorecorder.source.DefaultAudioSource
import kotlinx.android.synthetic.main.activity_login.*
import java.io.File

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val subKey = "56cf040fd80a4687b344077f7566bd83"
        val accKey = "f71b7467-aa6e-42f7-8fd1-8c4d08da9570"

        val audioRecorder = NaraeAudioRecorder()

        button_start_recording.setOnClickListener{
            fun defaultConfig() = AudioRecordConfig(MediaRecorder.AudioSource.MIC,
                AudioFormat.ENCODING_PCM_16BIT,
                AudioFormat.CHANNEL_IN_MONO,
                16000)

            val fileName = System.currentTimeMillis().toString()
            val destFile = File(getExternalFilesDir(null), "$fileName.wav")
            audioRecorder.create() {
                this.destFile = destFile
                this.recordConfig = defaultConfig()
            }

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&  ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                val permissions = arrayOf(android.Manifest.permission.RECORD_AUDIO, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(this, permissions,0)
            } else {
                audioRecorder.startRecording(applicationContext)
                val t = Toast.makeText(applicationContext,  "Started", Toast.LENGTH_LONG)
                t. show()
            }
        }

        button_stop_recording.setOnClickListener{
            audioRecorder.stopRecording()

            val t = Toast.makeText(applicationContext, "Stopped", Toast.LENGTH_LONG)
            t. show()
        }
    }
}
