package com.example.chatapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.chatapp.view.ui.RegisterActivity0
import org.junit.Rule
import org.junit.Test

class RegisterTest {
    @get:Rule
    var intentsRule: IntentsTestRule<RegisterActivity0> = IntentsTestRule(RegisterActivity0::class.java)

    @Test
    fun createNewAzureProfile(){
        onView(withId(R.id.register_createProfile_btn)).perform(click())
    }

    @Test
    fun loginWithExistingAzureProfile(){
        onView((withId(R.id.register_profileId)))
            .perform(typeText("1090d84a-4790-472b-b5eb-7475426bbc3"))
        onView(withId(R.id.next_page_btn)).perform(click())
    }
}