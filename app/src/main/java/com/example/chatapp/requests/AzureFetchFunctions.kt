package com.example.chatapp.requests

import android.nfc.Tag
import android.provider.Settings
import android.util.Log
import android.widget.*
import com.example.chatapp.GeneralFunctions
import com.example.chatapp.loginActivities.LoginActivity1
import com.example.chatapp.registerActivities.RegisterActivity0
import com.example.chatapp.registerActivities.RegisterActivity1
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import kotlinx.coroutines.*
import okhttp3.RequestBody
import okhttp3.ResponseBody
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
                RegisterActivity0().accessRegister1(it.id)
            }.onFailure {
                it.printStackTrace()
            }
        }

    }
    private suspend fun createEnrollment(body: RequestBody, profileId: String): String {
        var res: retrofit2.Response<ResponseBody>? = null

        try{
            res = apiAzure.createEnrollment(
                "56cf040fd80a4687b344077f7566bd83",
                "application/octet-stream",
                profileId,
                false,
                body
            )
        } catch(e: Exception){
            e.printStackTrace()
        }
        return res!!.message()
    }

    private suspend fun getProfile(profileId: String): Double {
        var res: GetAzureProfile? = null
        try {
            res = apiAzure.getProfile(
                "56cf040fd80a4687b344077f7566bd83",
                profileId
            )
        } catch (e: Exception){
            e.printStackTrace()
        }
        return res!!.remainingEnrollmentSpeechTime
    }

    fun createVerifyEnrollment(body: RequestBody, profileId: String){
        GlobalScope.launch(Dispatchers.Main) {
            val createEnrollment = async { createEnrollment(body, profileId) }
            Log.d("Status", createEnrollment.await())
            val getProfile = async { getProfile(profileId) }
            val profile = getProfile.await()
            if (profile > 0.0){
                RegisterActivity1().verifyEnrollment(false)
            } else {
                RegisterActivity1().verifyEnrollment(true)
            }
        }
    }

    fun identifyEnrollment(body: RequestBody, profileId: String) {
        GlobalScope.launch(Dispatchers.Main){
            val identifyAudio = async { identifyAudio(body, profileId) }
            val operationId = identifyAudio.await()
            var statusVerifier = true
            var stat: Operations? = null
            while(statusVerifier){
                val getStatus = async { getOperationStatus(operationId!!) }
                stat = getStatus.await()
                statusVerifier = stat?.status == "notstarted" || stat?.status == "running"
            }
            LoginActivity1().accessMenu(stat?.confidence?.confidence!!)
        }
    }
    private suspend fun identifyAudio(body: RequestBody, profileId: String): String? {
        var header: String? = null
        println(profileId)
        try {
            val res = apiAzure.identifyEnrollment(
                "56cf040fd80a4687b344077f7566bd83",
                "application/octet-stream",
                profileId,
                true,
                body
            )
            header = res.headers().get("Operation-Location")
            header = header?.substring(63)
            println(header)
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