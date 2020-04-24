package com.example.chatapp.model.data

import com.google.gson.annotations.SerializedName

class AzureProfile(
    @SerializedName("identificationProfileId")
    val identificationProfileId: String,
    @SerializedName("enrollmentSpeechTime")
    val enrollmentSpeechTime: Double,
    @SerializedName("remainingEnrollmentSpeechTime")
    val remainingEnrollmentSpeechTime: Double,
    @SerializedName("locale")
    val locale: String,
    @SerializedName("createdDateTime")
    val createdDateTime: String,
    @SerializedName("lastActionDateTime")
    val lastActionDateTime: String,
    @SerializedName("enrollmentStatus")
    val enrollmentStatus: String
)