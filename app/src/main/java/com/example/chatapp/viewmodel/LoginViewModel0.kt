package com.example.chatapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.model.remote.ApiChatImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel0 : ViewModel() {
    val userDetails = MutableLiveData<String>()

    fun getUser(email: String){
        viewModelScope.launch(Dispatchers.Default) {
            kotlin.runCatching {
                ApiChatImpl().getUser(email)
            }.onSuccess {
                userDetails.postValue(it.id)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

}