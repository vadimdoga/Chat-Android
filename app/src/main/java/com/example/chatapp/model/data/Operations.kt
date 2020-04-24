package com.example.chatapp.model.data

import com.google.gson.annotations.SerializedName

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