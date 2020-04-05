package com.example.chatapp.requests

import android.widget.*
import com.example.chatapp.GeneralFunctions
import com.example.chatapp.registerActivities.RegisterActivity1
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import kotlinx.coroutines.*
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class AzureFetchFunctions{

    private val apiAzure = Retrofit.Builder()
        .baseUrl("https://vadim.cognitiveservices.azure.com/spid/v1.0/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiAzure::class.java)

    fun createProfile() {
        val jsonObj = GeneralFunctions().createJsonBody(arrayListOf("locale"), arrayListOf("en-us"), 1)

        CoroutineScope(Dispatchers.Main).launch {
            kotlin.runCatching {
                apiAzure.createProfile(
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
                apiAzure.createEnrollment(
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
            val res = apiAzure.identifyEnrollment(
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
            res = apiAzure.getOperationStatus(
                "56cf040fd80a4687b344077f7566bd83",
                id
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return res
    }
    fun getPhrases(adapter: ArrayAdapter<String>){
        GlobalScope.launch(Dispatchers.Main) {
            try {
                val res = apiAzure.getPhrases(
                    "56cf040fd80a4687b344077f7566bd83",
                    "en-US"
                )
                res.forEach{
                    RegisterActivity1.addToArray(it.phrase)
                }
                adapter.notifyDataSetChanged()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}