package com.wesupport.accessibility.mysampledpc.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageInstaller

private const val ACTION_UNINSTALL_COMPLETE = "UNINSTALL_COMPLETE"


class PackageInstallationUtils {

    fun uninstallPackage(context: Context, packageName: String) {
        val packageInstaller: PackageInstaller = context.packageManager.packageInstaller
        packageInstaller.uninstall(packageName, createUninstallIntentSender(context, packageName))
    }

    private fun createUninstallIntentSender(context: Context, packageName: String): IntentSender {
        val intent = Intent(ACTION_UNINSTALL_COMPLETE)
        intent.putExtra(Intent.EXTRA_PACKAGE_NAME, packageName)
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        return pendingIntent.intentSender
    }
}