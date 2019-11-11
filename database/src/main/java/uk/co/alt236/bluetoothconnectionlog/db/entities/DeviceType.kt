package uk.co.alt236.bluetoothconnectionlog.db.entities

import android.bluetooth.BluetoothDevice

enum class DeviceType(internal val androidType: Int) {
    UNKNOWN(BluetoothDevice.DEVICE_TYPE_UNKNOWN),
    CLASSIC(BluetoothDevice.DEVICE_TYPE_CLASSIC),
    LE(BluetoothDevice.DEVICE_TYPE_LE),
    DUAL(BluetoothDevice.DEVICE_TYPE_DUAL);


    companion object {
        fun fromAndroidType(androidType: Int): DeviceType {
            for (enum in values()) {
                if (enum.androidType == androidType) {
                    return enum
                }
            }
            return UNKNOWN
        }
    }
}