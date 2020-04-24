package com.example.chatapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.model.remote.ApiAzureImpl
import com.example.chatapp.utils.GeneralFunctions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel0 : ViewModel() {
    val azureId = MutableLiveData<String>()
    private val subKey = "56cf040fd80a4687b344077f7566bd83"
    private val contentType = "application/json"
    private val jsonObj = GeneralFunctions().createJsonBody(arrayListOf("locale"), arrayListOf("en-us"))

    fun createProfile() {
        viewModelScope.launch(Dispatchers.Default) {
            kotlin.runCatching {
                ApiAzureImpl().createProfile(subKey, contentType, jsonObj)
            }.onSuccess {
                azureId.postValue(it.id)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }
}