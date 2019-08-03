package uk.co.alt236.bluetoothconnectionlog.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

class LocationPermissionCheck(private val context: Context) {

    fun checkLocationPermission(): Result {
        val hasLocationPermission =
            hasPermisson(Manifest.permission.ACCESS_COARSE_LOCATION) || hasPermisson(Manifest.permission.ACCESS_FINE_LOCATION)
        if (!hasLocationPermission) {
            return Result.DENIED
        }

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val hasBackgroundPermission = hasPermisson(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            if (hasBackgroundPermission) {
                Result.GRANTED
            } else {
                Result.GRANTED_FOREGROUND_ONLY
            }
        } else {
            Result.GRANTED
        }
    }

    private fun hasPermisson(permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    enum class Result {
        DENIED,
        GRANTED,
        GRANTED_FOREGROUND_ONLY
    }
}