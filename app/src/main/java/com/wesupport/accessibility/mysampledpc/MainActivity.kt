package com.wesupport.accessibility.mysampledpc

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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

    }
}