package com.example.chatapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//todo: get profile details

class ChatMenuViewModel : ViewModel(){
    val profileDetails = MutableLiveData<JsonElement>()

    fun getProfileDetails(){
        viewModelScope.launch(Dispatchers.Main) {
            //todo: make a request field for profile
//            val joke = ApiServiceImpl().getJoke()
//            profileDetails.postValue(joke)
        }
    }
}