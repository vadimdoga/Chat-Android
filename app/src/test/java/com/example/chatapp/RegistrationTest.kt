package com.example.chatapp
import android.util.Log
import com.example.chatapp.utils.RegisterCheck
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Test

class RegistrationTest{
    private val registrationCheck = RegisterCheck()

    @Test
    fun checkMail(){
        print("test mail")
        val mail = registrationCheck.isMailValid("blabla@gmail.com")
        print(mail)
        assertThat(mail, `is`(true))
    }
    @Test
    fun checkPassword(){
        print("test password")
        val password = registrationCheck.isPasswordValid("123456", "123456")
        assertThat(password, `is`(true))

    }
}