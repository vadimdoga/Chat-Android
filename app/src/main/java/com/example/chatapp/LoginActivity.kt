package com.example.chatapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.chatapp.req.ApiService
import com.example.chatapp.req.Phrases
import com.example.chatapp.req.Profile
import com.github.squti.androidwaverecorder.WaveRecorder
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://vadim.cognitiveservices.azure.com/spid/v1.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)

        api.getPhrases().enqueue(object: Callback<List<Phrases>>{
            override fun onResponse(call: Call<List<Phrases>>, response: Response<List<Phrases>>) {
                println(response.body())
            }

            override fun onFailure(call: Call<List<Phrases>>, t: Throwable) {
                println("error")
            }

        })

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
