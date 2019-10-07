package uk.co.alt236.bluetoothconnectionlog.permissions

import android.app.Activity

class PermissionCheckerWrapper(activity: Activity) {

    private val permissionLogicList =
        listOf(AndroidPermissionChecker(activity) as PermissionChecker)

    fun needToAsk(): Boolean {
        var retVal = false

        for (logic in permissionLogicList) {
            if (logic.isNeeded()) {
                retVal = true
                break
            }
        }

        return retVal
    }


}
