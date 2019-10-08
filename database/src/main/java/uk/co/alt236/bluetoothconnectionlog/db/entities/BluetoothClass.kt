package uk.co.alt236.bluetoothconnectionlog.db.entities

import android.bluetooth.BluetoothClass
import java.io.Serializable

data class BluetoothClass(val deviceClass: Int, val majorDeviceClass: Int) : Serializable {

    constructor(btDevice: BluetoothClass) : this(btDevice.deviceClass, btDevice.majorDeviceClass)
}