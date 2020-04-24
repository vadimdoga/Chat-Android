package com.example.chatapp.model.remote

import com.example.chatapp.model.data.CreateProfile
import com.example.chatapp.model.data.AzureProfile
import com.example.chatapp.model.data.Operations
import com.example.chatapp.model.data.Phrases
import com.google.gson.JsonElement
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiAzure {

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
    ): retrofit2.Response<ResponseBody>

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

    @GET("identificationProfiles/{identificationProfileId}")
    suspend fun getProfile(
        @Header("Ocp-Apim-Subscription-Key") subKey: String,
        @Path("identificationProfileId") profileId: String
    ) : AzureProfile



}