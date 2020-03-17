package com.example.chatapp.req

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


private val subKey = "56cf040fd80a4687b344077f7566bd83"
private val accKey = "f71b7467-aa6e-42f7-8fd1-8c4d08da9570"

interface ApiService {
    @Headers("Ocp-Apim-Subscription-Key: 56cf040fd80a4687b344077f7566bd83")
    @GET("identificationProfiles")
    fun getProfiles(): Call<List<Profile>>

    @Headers("Ocp-Apim-Subscription-Key: 56cf040fd80a4687b344077f7566bd83")
    @GET("verificationPhrases?locale=en-US")
    fun getPhrases(): Call<List<Phrases>>
}