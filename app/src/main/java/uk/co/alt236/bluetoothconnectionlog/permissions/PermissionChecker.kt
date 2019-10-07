package uk.co.alt236.bluetoothconnectionlog.permissions

interface PermissionChecker {
    fun isNeeded(): Boolean
}
