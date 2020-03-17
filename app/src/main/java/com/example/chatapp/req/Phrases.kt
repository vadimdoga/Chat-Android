package com.example.chatapp.req

import com.google.gson.annotations.SerializedName

data class Phrases(
    @SerializedName("phrase")
    val phrase: String
)