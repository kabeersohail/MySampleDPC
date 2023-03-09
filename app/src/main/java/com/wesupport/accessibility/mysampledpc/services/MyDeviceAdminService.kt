package com.wesupport.accessibility.mysampledpc.services

import android.app.admin.DeviceAdminService
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import com.wesupport.accessibility.mysampledpc.PackageMonitorReceiver

@RequiresApi(api = VERSION_CODES.O)
class DeviceAdminService : DeviceAdminService() {
    private var mPackageChangedReceiver: BroadcastReceiver? = null
    override fun onCreate() {
        super.onCreate()
        registerPackageChangesReceiver()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterPackageChangesReceiver()
    }

    private fun registerPackageChangesReceiver() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_PACKAGE_ADDED)
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED)
        intentFilter.addDataScheme("package")
        mPackageChangedReceiver = PackageMonitorReceiver()
        applicationContext.registerReceiver(mPackageChangedReceiver, intentFilter)
    }

    private fun unregisterPackageChangesReceiver() {
        if (mPackageChangedReceiver != null) {
            applicationContext.unregisterReceiver(mPackageChangedReceiver)
            mPackageChangedReceiver = null
        }
    }
}