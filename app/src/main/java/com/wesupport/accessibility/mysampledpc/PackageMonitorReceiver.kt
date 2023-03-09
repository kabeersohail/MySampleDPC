package com.wesupport.accessibility.mysampledpc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.wesupport.accessibility.mysampledpc.extensions.TAG

class PackageMonitorReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val mContext: Context = context ?: run {
            Log.d(TAG, "context is null")
            return
        }

        val mIntent: Intent = intent ?: run {
            Log.d(TAG, "intent is null")
            return
        }

        if(Intent.ACTION_PACKAGE_REMOVED != mIntent.action) {
            Log.d(TAG, "No package is removed")
            return
        }

        val removedPackageName: String = getPackageNameFromIntent(intent) ?: run {
            Log.d(TAG, "unable to fetch removed package name")
            return
        }

        Log.d(TAG, "Removed package name id $removedPackageName")
    }

    private fun getPackageNameFromIntent(intent: Intent): String? {
        return intent.data?.schemeSpecificPart
    }
}