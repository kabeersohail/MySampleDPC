package com.wesupport.accessibility.mysampledpc

import android.content.pm.PackageManager
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
                myWifiManager.removeAllNetworks()
            } else {
                // Permission is denied, handle it accordingly
                Log.d(TAG, "Access fine location is not granted")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        requestLocationPermission()

        binding.saveWifiConfiguration.setOnClickListener {
            Log.d(TAG, "saveWifiConfiguration button clicked")

            myWifiManager.wifiConfigurationCompat(
                "We-Share",
                2,
                "Wen@ble@20$3"
            )
        }

        binding.removeWifiConfiguration.setOnClickListener {

            val wifiManager = applicationContext.getSystemService(WifiManager::class.java)

            val config: WifiConfiguration = myWifiManager.getConfiguredNetworksDO(wifiManager).firstOrNull { it.SSID == myWifiManager.getQuotedString("We-Share") } ?: run {
                Toast.makeText(
                    this,
                    "Not available",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            myWifiManager.removeNetwork(wifiManager, config.networkId)
        }

        binding.removeAllNetworks.setOnClickListener {
            requestLocationPermission()
        }

        binding.removeAllNetworksNotOwnedByDo.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                myWifiManager.removeAllNetworksNotOwnedByDO()
            } else {
                Toast.makeText(this, "Option not available", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun requestLocationPermission() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                "android.permission.ACCESS_FINE_LOCATION"
            ) -> {
                myWifiManager.removeAllNetworks()
            }
            else -> {
                // Permission is not granted, request it
                requestPermissionLauncher.launch("android.permission.ACCESS_FINE_LOCATION")
            }
        }
    }

}