package com.example.chatapp.requests

import com.google.gson.annotations.SerializedName

class CreateProfile {
    @SerializedName("identificationProfileId")
    val id: String = ""
}

class Operations {
    @SerializedName("status")
    val status: String = ""
    @SerializedName("createdDateTime")
    val d: String = ""
    @SerializedName("lastActionDateTime")
    val l: String = ""
    @SerializedName("processingResult")
    val confidence: Confidence? = null
}

class Confidence {
    @SerializedName("identifiedProfileId")
    val profileId: String = ""
    @SerializedName("confidence")
    val confidence: String = ""
}

class Phrases {
    @SerializedName("phrase")
    val phrase: String = ""
}

class AzureId {
    @SerializedName("azureId")
    val id: String = ""
}