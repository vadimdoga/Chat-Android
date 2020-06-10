package com.example.chatapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.chatapp.view.ui.LoginActivity0

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class LoginTest {
    @get:Rule
    var intentsRule: IntentsTestRule<LoginActivity0> = IntentsTestRule(LoginActivity0::class.java)

    @Test
    fun checkLogin() {
        onView(withId(R.id.email_input_login))
            .perform(typeText("vadim.doga1@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.login_verify_email)).perform(click())
    }
}
