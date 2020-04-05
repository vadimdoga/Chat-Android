package com.example.chatapp.requests

import com.google.gson.JsonElement
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiChat{

    @POST("/user/register")
    suspend fun createUser(
        @Body body: JsonElement
    ): Operations

    @GET("/user/login")
    suspend fun getUser(
        @Query("email") email: String
    ) : AzureId

}