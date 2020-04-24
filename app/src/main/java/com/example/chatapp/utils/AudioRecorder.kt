package com.example.chatapp.utils

import android.content.Context
import android.widget.Toast
import com.github.squti.androidwaverecorder.WaveRecorder
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class AudioRecorder(var filePath: String, var context: Context) {
    var waveRecorder: WaveRecorder? = null

    fun startRecording(){
        waveRecorder = WaveRecorder(filePath)
        waveRecorder?.startRecording()
        val t = Toast.makeText(context,  "Record On", Toast.LENGTH_LONG)
        t. show()
    }

    fun stopRecording(){
        waveRecorder?.stopRecording()
        val t = Toast.makeText(context, "Record Off", Toast.LENGTH_LONG)
        t. show()
    }

    fun getBinary(): RequestBody {
        val myFile = File(filePath)
        val ins: InputStream = FileInputStream(myFile)
        val content = ins.readBytes()
        return RequestBody.create(MediaType.parse("application/octet-stream"), content)
    }
}