package com.wesupport.accessibility.mysampledpc.utils

import android.content.Context
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import com.wesupport.accessibility.mysampledpc.extensions.TAG

@Suppress("Deprecation")
object WifiConfigUtil {

    private const val INVALID_NETWORK_ID = -1

    fun saveWifiConfiguration(context: Context, wifiConfiguration: WifiConfiguration): Boolean {
        Log.d(TAG, "saveWifiConfiguration:: ")


        val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val networkId: Int = if (wifiConfiguration.networkId == INVALID_NETWORK_ID) {
            addWifiNetwork(wifiManager, wifiConfiguration)
        } else {
            updateWifiNetwork(wifiManager, wifiConfiguration)
        }

        if (networkId == INVALID_NETWORK_ID) {
            Log.d(TAG, "saveWifiConfiguration:: INVALID_NETWORK_ID $networkId")

            return false
        }

        Log.d(TAG, "saveWifiConfiguration:: successful $networkId")
        wifiManager.enableNetwork(networkId,  /* disableOthers= */false)
        return true
    }

    private fun addWifiNetwork(wifiManager: WifiManager, wifiConfiguration: WifiConfiguration): Int {
        Log.d(TAG, "addWifiNetwork:: ")

        // WifiManager APIs are marked as deprecated but still explicitly supported for DPCs.
        val networkId = wifiManager.addNetwork(wifiConfiguration)
        Log.d(TAG, "addWifiNetwork:: networkID is $networkId")

        if (networkId == INVALID_NETWORK_ID) {
            return INVALID_NETWORK_ID
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            // Saving the configuration is required pre-O.
            if (!saveAddedWifiConfiguration(wifiManager, networkId)) {
                return INVALID_NETWORK_ID
            }
        }
        return networkId
    }

    private fun saveAddedWifiConfiguration(wifiManager: WifiManager, networkId: Int): Boolean {
        Log.d(TAG, "saveAddedWifiConfiguration:: ")


        val saveConfigurationSuccess = wifiManager.saveConfiguration()
        if (!saveConfigurationSuccess) {
            wifiManager.removeNetwork(networkId)
            return false
        }
        return true
    }

    private fun updateWifiNetwork(
        wifiManager: WifiManager, wifiConfiguration: WifiConfiguration
    ): Int {
        Log.d(TAG, "updateWifiNetwork:: ")


        // WifiManager APIs are marked as deprecated but still explicitly supported for DPCs.
        val networkId = wifiManager.updateNetwork(wifiConfiguration)
        if (networkId == INVALID_NETWORK_ID) {
            return INVALID_NETWORK_ID
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            // Saving the configuration is required pre-O.
            if (!saveUpdatedWifiConfiguration(wifiManager)) {
                return INVALID_NETWORK_ID
            }
        }
        return networkId
    }

    private fun saveUpdatedWifiConfiguration(wifiManager: WifiManager): Boolean {
        Log.d(TAG, "saveUpdatedWifiConfiguration" +
                ":: ")

        return wifiManager.saveConfiguration()
    }

}