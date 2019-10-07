package uk.co.alt236.bluetoothconnectionlog.permissions

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import java.util.*

class AndroidPermissionChecker(context: Context) : PermissionChecker {

    private val mContext: Context = context.applicationContext

    fun getRequiredPermissions(): Array<String> {
        val packageName = mContext.packageName
        val packageManager = mContext.packageManager

        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS)
            return if (info.requestedPermissions != null) {
                info.requestedPermissions
            } else {
                emptyArray()
            }

        } catch (e: PackageManager.NameNotFoundException) {
            throw IllegalStateException(e.message, e)
        }
    }

    private fun hasAllPermissions(permissions: Array<String>): Boolean {
        for (perm in permissions) {
            if ((ActivityCompat.checkSelfPermission(
                    mContext,
                    perm
                ) != PackageManager.PERMISSION_GRANTED)
            ) {
                Log.d("ANDROID_PERM", "Missing $perm")
                return false
            }
        }
        Log.d("ANDROID_PERM", "Has all permissions!")
        return true
    }

    override fun isNeeded(): Boolean {
        val permissions = getRequiredPermissions()
        Log.d("ANDROID_PERM", Arrays.toString(permissions))
        return !hasAllPermissions(permissions)
    }
}
