package com.wesupport.accessibility.mysampledpc

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.wesupport.accessibility.mysampledpc.adminreciever.MyAdminReceiver
import com.wesupport.accessibility.mysampledpc.advancedcertificatemanagement.AdvancedCertificateManagement
import com.wesupport.accessibility.mysampledpc.advancedcertificatemanagement.SetupCertificate
import com.wesupport.accessibility.mysampledpc.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val adminComponentName = ComponentName(this, MyAdminReceiver::class.java)
        val advancedCertificateManagement = AdvancedCertificateManagement(
            dpm,
            adminComponentName
        )

        storeCertificateInInternalStorage()

        binding.installCertificate.setOnClickListener {
            advancedCertificateManagement.installACertificate()
        }

        binding.getInstalledCertificates.setOnClickListener {
            val installedCertificates = advancedCertificateManagement.getCaCertificateSubjectDnList()
            Toast.makeText(this, installedCertificates?.toList().toString(), Toast.LENGTH_SHORT).show()
        }

        binding.removeAllInstalledCertificate.setOnClickListener {
            advancedCertificateManagement.removeAllInstalledCertificates()
            Toast.makeText(this, "All CA certificates have been removed.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun storeCertificateInInternalStorage() {
        val rawResId = R.raw.ca // Replace with your certificate's raw resource ID

        val setupCertificate = SetupCertificate()

        val inputStream = setupCertificate.getInputStreamFromRawResource(this, rawResId)
        setupCertificate.copyToInternalStorage(this, inputStream, "ca.crt")
    }
}