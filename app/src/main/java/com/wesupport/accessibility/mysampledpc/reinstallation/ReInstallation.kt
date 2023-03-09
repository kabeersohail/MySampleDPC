package com.wesupport.accessibility.mysampledpc.reinstallation

import android.content.Context
import android.util.Log
import com.wesupport.accessibility.mysampledpc.extensions.TAG
import com.wesupport.accessibility.mysampledpc.utils.PackageInstallationUtils

class ReInstallation(
    private val context: Context
) {

    fun PackageModel.performLogic() {
        if(!isPackageInstalled()) {
            Log.d(TAG, "The requested package is not installed")
            return
        }

        Log.d(TAG, "The requested package is installed")

        val currentInstalledVersion: String = fetchCurrentInstalledVersion()

        if(currentInstalledVersion >= newPackageVersion) {
            Log.d(TAG, "The installed version is already up-to-date $currentInstalledVersion >= $newPackageVersion")
            return
        }

        if(shouldReInstall) {
            goAheadWithReInstallLogic()
        }

    }

    private fun PackageModel.isPackageInstalled(): Boolean {
        Log.d(TAG, "Checking if $packageName is installed")
        return true
    }

    private fun PackageModel.fetchCurrentInstalledVersion(): String {
        Log.d(TAG, "Checking current installed version of $packageName")
        return "Current.Installed.Version"
    }

    private fun PackageModel.goAheadWithReInstallLogic() {
        val isPackageUninstalled = uninstallPackage()

        if(!isPackageUninstalled) {
            Log.d(TAG, "Uninstallation of $packageName failed")
            return
        }

        Log.d(TAG, "Uninstallation of $packageName is successful")

        val isPackageInstalled = installPackage()

        if(!isPackageInstalled) {
            Log.d(TAG, "Re-installation of $packageName has failed")
        }

        Log.d(TAG, "Re-installation of $packageName is successful")
    }

    private fun PackageModel.uninstallPackage(): Boolean {
        Log.d(TAG, "Uninstalling $packageName")
        PackageInstallationUtils().uninstallPackage(context, packageName)
        return true
    }

    private fun PackageModel.installPackage(): Boolean {
        Log.d(TAG, "Installing $packageName")
        return true
    }
}