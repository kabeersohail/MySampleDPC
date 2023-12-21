package com.wesupport.accessibility.mysampledpc.advancedcertificatemanagement

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.util.Log
import com.wesupport.accessibility.mysampledpc.extensions.TAG
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate

const val DEFAULT_BUFFER_SIZE = 4096
const val X509_CERT_TYPE = "X.509"

class AdvancedCertificateManagement(
    private val devicePolicyManager: DevicePolicyManager,
    private val adminComponent: ComponentName
) {

    private fun uninstallAllCaCertificates() {
        devicePolicyManager.uninstallAllUserCaCerts(adminComponent)
    }

    private fun getCaCertificateSubjectDnList(): Array<String?>? {
        val installedCaCerts: List<ByteArray> = devicePolicyManager.getInstalledCaCerts(adminComponent)
        var caSubjectDnList: Array<String?>? = null
        if (installedCaCerts.isNotEmpty()) {
            caSubjectDnList = arrayOfNulls(installedCaCerts.size)
            var i = 0
            for (installedCaCert in installedCaCerts) {
                try {
                    val certificate = CertificateFactory.getInstance(X509_CERT_TYPE).generateCertificate(
                        ByteArrayInputStream(installedCaCert)
                    ) as X509Certificate
                    caSubjectDnList[i++] = certificate.subjectDN.name
                } catch (e: CertificateException) {
                    Log.e(TAG, "getCaCertificateSubjectDnList: $e")
                }
            }
        }
        return caSubjectDnList
    }

    private fun installCaCertificate(certificateInputStream: InputStream?): Boolean {
        try {
            if (certificateInputStream != null) {
                val byteBuffer = ByteArrayOutputStream()
                val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                var len = 0
                while (certificateInputStream.read(buffer).also { len = it } > 0) {
                    byteBuffer.write(buffer, 0, len)
                }
                return devicePolicyManager.installCaCert(adminComponent, byteBuffer.toByteArray())
            }
        } catch (e: IOException) {
            Log.e(TAG, "installCaCertificate: $e")
        }
        return false
    }

    private fun getInputStreamFromFilePath(filePath: String): InputStream {
        return FileInputStream(filePath)
    }


    fun execute() {
        val filePath = "/data/user/0/com.wesupport.accessibility.mysampledpc/files/ca.crt"
        val certificateInputStream = getInputStreamFromFilePath(filePath)
        val success = installCaCertificate(certificateInputStream)
    }

}