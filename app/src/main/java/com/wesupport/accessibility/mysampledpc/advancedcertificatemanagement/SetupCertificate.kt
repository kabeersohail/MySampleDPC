package com.wesupport.accessibility.mysampledpc.advancedcertificatemanagement

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class SetupCertificate {

    fun getInputStreamFromRawResource(context: Context, rawResId: Int): InputStream {
        return context.resources.openRawResource(rawResId)
    }

    fun copyToInternalStorage(context: Context, inputStream: InputStream, filename: String) {
        try {
            val file = File(context.filesDir, filename)
            val outputStream = FileOutputStream(file)

            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }
            outputStream.flush()
            outputStream.close()
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}