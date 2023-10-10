package com.example.gmc_2.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

object FileUtil {
    fun compressImageByteArray(data: ByteArray): ByteArray {
        try { //"data" is your "byte[] data"
            val blob = ByteArrayOutputStream()
            val bmp = BitmapFactory.decodeByteArray(data, 0, data.size)
            bmp.compress(Bitmap.CompressFormat.WEBP, 70, blob) //100-best quality
            return blob.toByteArray()
        } catch (e: Exception) {
            Log.d("Error", "Image " + data.size + " not saved: " + e.message)
        }
        return byteArrayOf()
    }

    @Throws(IOException::class)
    fun getBytes(inputStream: InputStream): ByteArray? {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len = 0
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }
}