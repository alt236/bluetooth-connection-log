package uk.co.alt236.bluetoothconnectionlog.db.entities

import android.bluetooth.BluetoothClass

data class BluetoothClass(val deviceClass: Int, val majorDeviceClass: Int) {

    constructor(btDevice: BluetoothClass) : this(btDevice.deviceClass, btDevice.majorDeviceClass)
}