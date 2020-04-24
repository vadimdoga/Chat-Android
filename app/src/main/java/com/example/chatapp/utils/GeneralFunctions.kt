package com.example.chatapp.utils

import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import me.relex.circleindicator.CircleIndicator
import org.json.JSONObject

class GeneralFunctions: AppCompatActivity() {
    fun positionIndicator(count:Int, position: Int, indicator: CircleIndicator){
        indicator.createIndicators(count, position)
        indicator.animatePageSelected(position)
    }
    fun createJsonBody(key: ArrayList<String>, value: ArrayList<String>): JsonElement {
        val nrEl = key.size
        val bodyObj = JSONObject()
        for (i in 0 until nrEl){
            bodyObj.put(key[i], value[i])
        }
        val jsonParser = JsonParser()
        return jsonParser.parse(bodyObj.toString())
    }
}