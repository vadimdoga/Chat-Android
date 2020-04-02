package com.example.chatapp.requests

import com.google.gson.JsonElement
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*


private val azureKey = "56cf040fd80a4687b344077f7566bd83"
private val profileId = "877eb35e-8a56-416b-9cd2-9db729f83b07"

interface ApiService {

    @POST("identificationProfiles")
    suspend fun createProfile(
        @Header("Ocp-Apim-Subscription-Key") subKey: String,
        @Header("Content-Type") contentType: String,
        @Body body: JsonElement
    ): CreateProfile

    @POST("identificationProfiles/{identificationProfileId}/enroll")
    suspend fun createEnrollment(
        @Header("Ocp-Apim-Subscription-Key") subKey: String,
        @Header("Content-Type") contentType: String,
        @Path("identificationProfileId") profileId: String,
        @Query("shortAudio") shortAudio: Boolean,
        @Body audioFile: RequestBody
    ): Void

    @POST("identify")
    suspend fun identifyEnrollment(
        @Header("Ocp-Apim-Subscription-Key") subKey: String,
        @Header("Content-Type") contentType: String,
        @Query("identificationProfileIds") profileId: String,
        @Query("shortAudio") shortAudio: Boolean,
        @Body audioFile: RequestBody
    ): retrofit2.Response<ResponseBody>

    @GET("operations/{id}")
    suspend fun getOperationStatus(
        @Header("Ocp-Apim-Subscription-Key") subKey: String,
        @Path("id") id: String
    ): Operations

    @GET("verificationPhrases")
    suspend fun getPhrases(
        @Header("Ocp-Apim-Subscription-Key") subKey: String,
        @Query("locale") locale: String
    ) : List<Phrases>



}