package com.wesupport.accessibility.mysampledpc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.wesupport.accessibility.mysampledpc.databinding.ActivityMainBinding
import com.wesupport.accessibility.mysampledpc.utils.PackageInstallationUtils


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
}