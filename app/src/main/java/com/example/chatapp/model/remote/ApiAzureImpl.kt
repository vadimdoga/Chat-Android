package com.example.chatapp.model.remote


import com.example.chatapp.model.data.AzureProfile
import com.example.chatapp.model.data.CreateProfile
import com.example.chatapp.model.data.Operations
import com.example.chatapp.model.data.Phrases
import com.google.gson.JsonElement
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiAzureImpl : ApiAzure{

    private val apiAzure = Retrofit.Builder()
        .baseUrl("https://vadim.cognitiveservices.azure.com/spid/v1.0/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiAzure::class.java)

    override suspend fun createProfile(
        subKey: String,
        contentType: String,
        body: JsonElement
    ): CreateProfile {
        return apiAzure.createProfile(
            subKey,
            contentType,
            body
        )
    }

    override suspend fun createEnrollment(
        subKey: String,
        contentType: String,
        profileId: String,
        shortAudio: Boolean,
        audioFile: RequestBody
    ): Response<ResponseBody> {
        return apiAzure.createEnrollment(
            subKey,
            contentType,
            profileId,
            shortAudio,
            audioFile
        )
    }

    override suspend fun identifyEnrollment(
        subKey: String,
        contentType: String,
        profileId: String,
        shortAudio: Boolean,
        audioFile: RequestBody
    ): Response<ResponseBody> {
        return apiAzure.identifyEnrollment(
            subKey,
            contentType,
            profileId,
            shortAudio,
            audioFile
        )
    }

    override suspend fun getOperationStatus(subKey: String, id: String): Operations {
        return apiAzure.getOperationStatus(
            subKey,
            id
        )
    }

    override suspend fun getPhrases(subKey: String, locale: String): List<Phrases> {
        return apiAzure.getPhrases(
            subKey,
            locale
        )
    }

    override suspend fun getProfile(subKey: String, profileId: String): AzureProfile {
        return apiAzure.getProfile(
            subKey,
            profileId
        )
    }
}