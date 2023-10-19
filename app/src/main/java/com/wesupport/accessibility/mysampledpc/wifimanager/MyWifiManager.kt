package com.wesupport.accessibility.mysampledpc.wifimanager

import android.content.Context
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiNetworkSpecifier
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.wesupport.accessibility.mysampledpc.R
import com.wesupport.accessibility.mysampledpc.extensions.TAG
import com.wesupport.accessibility.mysampledpc.utils.DpcNumbers.FIFTY_EIGHT
import com.wesupport.accessibility.mysampledpc.utils.DpcNumbers.TEN
import com.wesupport.accessibility.mysampledpc.utils.DpcNumbers.TWENTY_SIX
import com.wesupport.accessibility.mysampledpc.utils.DpcNumbers.ZERO
import com.wesupport.accessibility.mysampledpc.utils.WIFISecurityType.SECURITY_TYPE_NONE
import com.wesupport.accessibility.mysampledpc.utils.WIFISecurityType.SECURITY_TYPE_WEP
import com.wesupport.accessibility.mysampledpc.utils.WIFISecurityType.SECURITY_TYPE_WPA
import com.wesupport.accessibility.mysampledpc.utils.WifiConfigUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MyWifiManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    @Suppress("Deprecation")
    private val mOldConfig: WifiConfiguration? = null

    fun wifiConfigurationCompat(ssid: String, securityType: Int, password: String) {
        Log.d(TAG, "wifiConfigurationCompat:: ssid: $ssid securityType: $securityType password: $password")

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            Log.d(TAG, "wifiConfigurationCompat:: calling wifiConfigurationFor29AndAbove()")
//            wifiConfigurationFor29AndAbove()
//        } else {
            @Suppress("Deprecation")
            val wifiConfiguration = WifiConfiguration()

            Log.d(TAG, "wifiConfigurationCompat:: calling wifiConfigurationFor28AndBelow()")
            wifiConfiguration.wifiConfigurationFor28AndBelow(ssid, securityType, password)
//        }
    }

    @Suppress("Deprecation")
    private fun WifiConfiguration.wifiConfigurationFor28AndBelow(ssid: String, securityType: Int, password: String) {
        Log.d(TAG, "wifiConfigurationFor28AndBelow:: ssid: $ssid securityType: $securityType password: $password")

        SSID = getQuotedString(ssid)

        updateConfigurationSecurity(securityType, password)

        if (mOldConfig != null) {
            Log.d(TAG, "wifiConfigurationFor28AndBelow:: old config is not null $mOldConfig")

            networkId = mOldConfig.networkId
        }

        val success: Boolean = WifiConfigUtil.saveWifiConfiguration(context, this)

        val messageResId = if (success) R.string.wifi_config_success else R.string.wifi_config_fail
        val message = context.getString(messageResId)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


    @Suppress("Deprecation")
    private fun WifiConfiguration.updateConfigurationSecurity(
        securityType: Int,
        password: String
    ) {
        Log.d(TAG, "updateConfigurationSecurity:: securityType $securityType password: $password")

        when (securityType) {
            SECURITY_TYPE_NONE -> allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)

            SECURITY_TYPE_WEP -> {

                allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE)
                allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN)
                allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.SHARED)

                val length = password.length

                if (length == ZERO) return

                if ((length == TEN || length == TWENTY_SIX || length == FIFTY_EIGHT) && password.matches(
                        Regex("[0-9A-Fa-f]*")
                    )
                ) {
                    wepKeys[ZERO] = password
                } else {
                    wepKeys[ZERO] = getQuotedString(password)
                }

                wepTxKeyIndex = ZERO
            }

            SECURITY_TYPE_WPA -> {
                allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
                val length = password.length

                if (length != ZERO) {
                    preSharedKey = if (password.matches("[0-9A-Fa-f]{64}".toRegex())) {
                        password
                    } else {
                        getQuotedString(password)
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun wifiConfigurationFor29AndAbove() {
        Log.d(TAG, "wifiConfigurationFor29AndAbove:: ")

        WifiNetworkSpecifier.Builder().build()
    }

    private fun getQuotedString(string: String): String {
        Log.d(TAG, "getQuotedString:: ")

        return "\"$string\""
    }

}