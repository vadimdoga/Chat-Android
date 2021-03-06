package com.example.chatapp.model.remote

import com.example.chatapp.model.data.AzureId
import com.google.gson.JsonElement
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiChat{

    @POST("/user/register")
    suspend fun createUser(
        @Body body: JsonElement
    ): retrofit2.Response<ResponseBody>

    @GET("/user/login")
    suspend fun getUser(
        @Query("email") email: String
    ) : AzureId

}