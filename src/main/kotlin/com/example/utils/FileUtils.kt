package com.example.utils

import java.io.File

object FileUtils {
    fun saveByteArrayToFile(byteArray: ByteArray, filePath: String): File {
        val file = File(filePath)
        file.writeBytes(byteArray)
        return file
    }
}