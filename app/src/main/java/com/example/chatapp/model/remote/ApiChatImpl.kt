package com.example.chatapp.model.remote

import android.util.Log
import com.example.chatapp.model.data.AzureId
import com.example.chatapp.utils.GeneralFunctions
import com.example.chatapp.view.ui.RegisterActivity1
import com.google.gson.JsonElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiChatImpl : ApiChat {
    private val apiChat = Retrofit.Builder()
        .baseUrl("http://192.168.0.3:4000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiChat::class.java)

    override suspend fun createUser(body: JsonElement): Response<ResponseBody> {
        return apiChat.createUser(body)
    }

    override suspend fun getUser(email: String): AzureId {
        return apiChat.getUser(email)
    }


    //todo: get profile details


}