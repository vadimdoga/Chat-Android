package com.example.chatapp

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModel
import com.example.chatapp.register.RegisterActivity1
import com.example.chatapp.req.ApiService
import com.example.chatapp.req.Operations
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import kotlinx.coroutines.*
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class FetchFunctions{

    private val api = Retrofit.Builder()
        .baseUrl("https://vadim.cognitiveservices.azure.com/spid/v1.0/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    fun createProfile() {
        val jsonObj = createJsonBody("locale", "en-us")

        CoroutineScope(Dispatchers.Main).launch {
            kotlin.runCatching {
                api.createProfile(
                    "56cf040fd80a4687b344077f7566bd83",
                    "application/json",
                    jsonObj
                )
            }.onSuccess {
                it.id
            }.onFailure {
                it.printStackTrace()
            }
        }

    }
    fun createEnrollment(body: RequestBody, profileId: String){
        GlobalScope.launch(Dispatchers.Main) {
            kotlin.runCatching {
                api.createEnrollment(
                    "56cf040fd80a4687b344077f7566bd83",
                    "application/octet-stream",
                    profileId,
                    false,
                    body
                )
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun identifyEnrollment(body: RequestBody, profileId: String) {
        GlobalScope.launch{
            val identifyAudio = async { identifyAudio(body, profileId) }
            val operationId = identifyAudio.await()
            var statusVerifier = true
            var stat: Operations? = null
            while(statusVerifier){
                val getStatus = async { getOperationStatus(operationId!!) }
                stat = getStatus.await()
                statusVerifier = stat?.status == "notstarted" || stat?.status == "running"
            }
            println(stat?.confidence?.confidence)
        }
    }
    private suspend fun identifyAudio(body: RequestBody, profileId: String): String? {
        var header: String? = null
        try {
            val res = api.identifyEnrollment(
                "56cf040fd80a4687b344077f7566bd83",
                "application/octet-stream",
                profileId,
                false,
                body
            )
            header = res.headers().get("Operation-Location")
            header = header?.substring(63)
        } catch (e: Exception){
            e.printStackTrace()
        }
        return header
}
    private suspend fun getOperationStatus(id: String): Operations?{
        var res: Operations? = null
        try{
            res = api.getOperationStatus(
                "56cf040fd80a4687b344077f7566bd83",
                id
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return res
    }
    private fun createJsonBody(key: String, value: String): JsonElement {
        val bodyObj = JSONObject()
        bodyObj.put(key, value)
        val jsonParser = JsonParser()
        return jsonParser.parse(bodyObj.toString())
    }
    fun getPhrases(adapter: ArrayAdapter<String>){
        val listPhrases = arrayListOf<String>()

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val res = api.getPhrases(
                    "56cf040fd80a4687b344077f7566bd83",
                    "en-US"
                )
                res.forEach{
//                    listPhrases.add(it.phrase)
                    RegisterActivity1.addToArray(it.phrase)
                }
                println(RegisterActivity1.phrList)
                adapter.notifyDataSetChanged()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}