package com.example.chatapp

import org.junit.Test
import com.example.chatapp.utils.GeneralFunctions
import org.hamcrest.core.Is
import org.junit.Assert
import java.io.File

class Audio {


    private val audio = GeneralFunctions()


    @Test
    fun getBinary(){
        val filename = "test.txt"
        val fileObject = File(filename)
        fileObject.writeText("This is some text for file writing operations")
        val bin = audio.getSimulatedAudioBinary(fileObject)
        val res = bin.contentLength() < 0
        Assert.assertThat(res, Is.`is`(false))

    }
}

