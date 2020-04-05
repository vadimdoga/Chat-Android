package com.example.chatapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import me.relex.circleindicator.CircleIndicator
import org.json.JSONObject

@SuppressLint("Registered")
class GeneralFunctions: AppCompatActivity() {
    fun positionIndicator(count:Int, position: Int, indicator: CircleIndicator){
        indicator.createIndicators(count, position)
        indicator.animatePageSelected(position)
    }
    fun checkPermission(): Boolean {
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
            return false
        } else return true
    }
    fun createJsonBody(key: ArrayList<String>, value: ArrayList<String>, nrEl: Int): JsonElement {
        val bodyObj = JSONObject()
        for (i in 0 until nrEl){
            bodyObj.put(key[i], value[i])
        }
        val jsonParser = JsonParser()
        return jsonParser.parse(bodyObj.toString())
    }
}