package com.example.chatapp.viewmodel

import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.model.data.Operations
import com.example.chatapp.model.data.Phrases
import com.example.chatapp.model.remote.ApiAzureImpl
import com.example.chatapp.view.ui.RegisterActivity1
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import java.lang.Exception

class LoginViewModel1 : ViewModel() {
    val phraseList = MutableLiveData<List<Phrases>>()
    val enrollmentStatus = MutableLiveData<String>()

    fun getPhrases() {
        val subKey = "56cf040fd80a4687b344077f7566bd83"
        val locale = "en-US"
        viewModelScope.launch(Dispatchers.Main) {
            kotlin.runCatching {
                ApiAzureImpl().getPhrases(subKey, locale)
            }.onSuccess {
                phraseList.postValue(it)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun identifyEnrollment(profileId: String, body: RequestBody) {
        val subKey = "56cf040fd80a4687b344077f7566bd83"
        val contentType = "application/octet-stream"
        val shortAudio = true
        var statusVerifier = true
        lateinit var stat : Operations
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val identifyEnrollment = async { ApiAzureImpl().identifyEnrollment(subKey, contentType, profileId, shortAudio, body) }
                val identifyEnrollmentResponse = identifyEnrollment.await()
                val identifyEnrollmentHeader = identifyEnrollmentResponse.headers().get("Operation-Location")
                val operationId = identifyEnrollmentHeader?.substring(63)

                while(statusVerifier){
                    val getStatus = async { ApiAzureImpl().getOperationStatus(subKey, operationId!!) }
                    stat = getStatus.await()
                    statusVerifier = stat.status == "notstarted" || stat.status == "running"
                }

                enrollmentStatus.postValue(stat.confidence?.confidence)
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }
}