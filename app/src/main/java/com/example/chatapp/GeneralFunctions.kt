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

class GeneralFunctions: AppCompatActivity() {
    fun positionIndicator(count:Int, position: Int, indicator: CircleIndicator){
        indicator.createIndicators(count, position)
        indicator.animatePageSelected(position)
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