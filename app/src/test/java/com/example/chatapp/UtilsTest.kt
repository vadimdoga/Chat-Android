package com.example.chatapp

import com.example.chatapp.utils.GeneralFunctions
import org.hamcrest.core.Is.`is`
import org.json.JSONObject
import org.junit.Assert.assertThat
import org.junit.Test


class UtilsTest {
    private val utilsTest = GeneralFunctions()

    @Test
    fun jsonParseTest(){
        val keyArr = arrayListOf("field1", "field2", "field3")
        val valArr = arrayListOf("1", "2", "3")
        val json = utilsTest.createJsonBody(keyArr, valArr)
        val res = json.isJsonNull
        assertThat(res, `is`(false))
    }
}