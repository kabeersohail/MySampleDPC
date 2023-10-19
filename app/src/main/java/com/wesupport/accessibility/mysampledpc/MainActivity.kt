package com.wesupport.accessibility.mysampledpc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.wesupport.accessibility.mysampledpc.databinding.ActivityMainBinding
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
        myWifiManager.execute()
    }
}