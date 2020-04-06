package com.example.chatapp.requests

import android.util.Log
import com.example.chatapp.GeneralFunctions
import com.example.chatapp.loginActivities.LoginActivity0
import com.example.chatapp.loginActivities.LoginActivity1
import com.example.chatapp.registerActivities.RegisterActivity1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatFetchFunctions {
    private val apiChat = Retrofit.Builder()
        .baseUrl("http://192.168.0.4:4000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiChat::class.java)

    fun createUser(email: String, azureId: String){
        val keys: ArrayList<String> = arrayListOf("email", "azureId")
        val values: ArrayList<String> = arrayListOf(email, azureId)
        val jsonObj = GeneralFunctions().createJsonBody(keys, values, 2)
        GlobalScope.launch(Dispatchers.Main) {
            kotlin.runCatching {
                apiChat.createUser(jsonObj)
            }.onSuccess {
                Log.e("Response", it.message())
                RegisterActivity1().profileExists(true)
            }.onFailure {
                RegisterActivity1().profileExists(false)
                it.printStackTrace()
            }
        }
    }

    fun getUser(email: String){
        GlobalScope.launch(Dispatchers.Main) {
            kotlin.runCatching {
                apiChat.getUser(email)
            }.onSuccess {
                LoginActivity0().accessLogin1(it.id)
            }.onFailure {
                it.printStackTrace()
            }
        }

    }


}