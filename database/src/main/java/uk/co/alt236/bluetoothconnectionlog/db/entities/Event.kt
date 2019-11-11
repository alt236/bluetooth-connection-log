package uk.co.alt236.bluetoothconnectionlog.db.entities

import android.bluetooth.BluetoothDevice

enum class Event {
    UNKNOWN,
    CONNECTED,
    DISCONNECT_REQUESTED,
    DISCONNECTED;

    companion object {
        fun fromAndroidEvent(event: String?): Event {
            return when (event) {
                BluetoothDevice.ACTION_ACL_CONNECTED -> CONNECTED
                BluetoothDevice.ACTION_ACL_DISCONNECTED -> DISCONNECTED
                BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED -> DISCONNECT_REQUESTED
                else -> UNKNOWN
            }
        }
    }
}