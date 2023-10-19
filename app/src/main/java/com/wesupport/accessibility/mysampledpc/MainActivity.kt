package com.wesupport.accessibility.mysampledpc

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.wesupport.accessibility.mysampledpc.databinding.ActivityMainBinding
import com.wesupport.accessibility.mysampledpc.extensions.TAG
import com.wesupport.accessibility.mysampledpc.wifimanager.MyWifiManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var myWifiManager: MyWifiManager

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                // Permission is granted, perform your actions here
                myWifiManager.removeNetwork()
            } else {
                // Permission is denied, handle it accordingly
                Log.d(TAG, "Access fine location is not granted")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.saveWifiConfiguration.setOnClickListener {
            Log.d(TAG, "saveWifiConfiguration button clicked")

            myWifiManager.wifiConfigurationCompat(
                "We-Share",
                2,
                "Wen@ble@20$3"
            )
        }

        binding.removeWifiConfiguration.setOnClickListener {
            requestLocationPermission()
        }
    }

    private fun requestLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                "android.permission.ACCESS_FINE_LOCATION"
            ) == PackageManager.PERMISSION_GRANTED -> {
                myWifiManager.removeNetwork()
            }
            else -> {
                // Permission is not granted, request it
                requestPermissionLauncher.launch("android.permission.ACCESS_FINE_LOCATION")
            }
        }
    }

}