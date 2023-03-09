package com.wesupport.accessibility.mysampledpc.reinstallation

data class PackageModel(
    val packageName: String,
    val newPackageVersion: String,
    val shouldReInstall: Boolean
)