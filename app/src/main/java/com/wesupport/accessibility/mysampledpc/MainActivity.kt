package com.wesupport.accessibility.mysampledpc

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.wesupport.accessibility.mysampledpc.adminreciever.MyAdminReceiver
import com.wesupport.accessibility.mysampledpc.advancedcertificatemanagement.AdvancedCertificateManagement
import com.wesupport.accessibility.mysampledpc.advancedcertificatemanagement.SetupCertificate
import com.wesupport.accessibility.mysampledpc.databinding.ActivityMainBinding
import com.wesupport.accessibility.mysampledpc.utils.PackageInstallationUtils


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val adminComponentName = ComponentName(this, MyAdminReceiver::class.java)

        storeCertificateInInternalStorage()

        binding.installCertificate.setOnClickListener {
            AdvancedCertificateManagement(
                dpm,
                adminComponentName
            ).execute()
        }
    }

    private fun storeCertificateInInternalStorage() {
        val rawResId = R.raw.ca // Replace with your certificate's raw resource ID

        val setupCertificate = SetupCertificate()

        val inputStream = setupCertificate.getInputStreamFromRawResource(this, rawResId)
        setupCertificate.copyToInternalStorage(this, inputStream, "ca.crt")

    }

}