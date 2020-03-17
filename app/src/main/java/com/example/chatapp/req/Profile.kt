package com.example.chatapp.req


import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("identificationProfileId")
    val identificationProfileId: String
)