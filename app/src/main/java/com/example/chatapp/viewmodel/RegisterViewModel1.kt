package com.example.chatapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.model.data.Phrases
import com.example.chatapp.model.remote.ApiAzureImpl
import com.example.chatapp.model.remote.ApiChatImpl
import com.example.chatapp.utils.GeneralFunctions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import java.lang.Exception

class RegisterViewModel1 : ViewModel() {
    val phraseList = MutableLiveData<List<Phrases>>()
    val enrollmentStatus = MutableLiveData<Boolean>()
    val createProfileStatus = MutableLiveData<Boolean>()

    fun getPhrases() {
        viewModelScope.launch(Dispatchers.Default) {
            kotlin.runCatching {
                val subKey = "56cf040fd80a4687b344077f7566bd83"
                val locale = "en-US"
                ApiAzureImpl().getPhrases(subKey, locale)
            }.onSuccess {
                phraseList.postValue(it)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun createVerifyEnrollment(body: RequestBody, profileId: String){
        val subKey = "56cf040fd80a4687b344077f7566bd83"
        val contentType = "application/octet-stream"
        val shortAudio = false
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val createEnrollment = async { ApiAzureImpl()
                    .createEnrollment(subKey, contentType, profileId, shortAudio, body) }
                val createEnrollmentResponse = createEnrollment.await()
                val createEnrollmentMessage = createEnrollmentResponse.message()
                Log.d("Status", createEnrollmentMessage)

                val getProfile = async { ApiAzureImpl().getProfile(subKey, profileId) }
                val profileResponse = getProfile.await()
                val remainingEnrollmentTime = profileResponse.remainingEnrollmentSpeechTime
                if (remainingEnrollmentTime > 0.0){
                    enrollmentStatus.postValue(false)
                } else {
                    enrollmentStatus.postValue(true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun createUser(email: String, azureId: String){
        val keys: ArrayList<String> = arrayListOf("email", "azureId")
        val values: ArrayList<String> = arrayListOf(email, azureId)
        val jsonObj = GeneralFunctions()
            .createJsonBody(keys, values)

        viewModelScope.launch(Dispatchers.Default) {
            kotlin.runCatching {
                ApiChatImpl().createUser(jsonObj)
            }.onSuccess {
                Log.e("Response", it.message())
                createProfileStatus.postValue(true)
            }.onFailure {
                createProfileStatus.postValue(false)
            }
        }
    }
}