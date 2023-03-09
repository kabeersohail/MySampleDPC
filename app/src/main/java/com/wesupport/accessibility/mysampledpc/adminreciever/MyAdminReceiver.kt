package com.wesupport.accessibility.mysampledpc.adminreciever

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.wesupport.accessibility.mysampledpc.extensions.TAG

class MyAdminReceiver: DeviceAdminReceiver() {

    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        Log.d(TAG, "Device Admin enabled")
    }
}