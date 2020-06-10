package com.example.chatapp.utils

class RegisterCheck{
    fun isMailValid(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+".toRegex()
        return if(email.isEmpty()) {
            false
        }else {
            email.trim().matches(emailPattern)
        }
    }
    fun isPasswordValid(val1: String, val2: String): Boolean {
        return if(val1.length > 5){
            val1 == val2
        } else {
            false
        }
    }
}