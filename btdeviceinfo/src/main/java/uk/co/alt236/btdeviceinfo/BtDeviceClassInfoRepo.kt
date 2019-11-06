@file:Suppress("unused")

package uk.co.alt236.btdeviceinfo

import android.annotation.SuppressLint
import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import uk.co.alt236.btdeviceinfo.enums.ParsedDeviceClass
import uk.co.alt236.btdeviceinfo.enums.ParsedDeviceMajorClass

class BtDeviceClassInfoRepo {
    private val iconMapper = DeviceIconMapper()

    @SuppressLint("MissingPermission")
    fun getInfoFor(device: BluetoothDevice): FriendlyClassInfo {
        return getInfoFor(device.bluetoothClass)
    }

    @SuppressLint("MissingPermission")
    fun getInfoFor(btClass: BluetoothClass): FriendlyClassInfo {
        return getInfoFor(btClass.deviceClass, btClass.majorDeviceClass)
    }

    @SuppressLint("MissingPermission")
    fun getInfoFor(deviceClass: Int, majorDeviceClass: Int): FriendlyClassInfo {
        val parsedClass = ParsedDeviceClass.fromAndroidIdentifier(deviceClass)
        val parsedMajorClass =
            ParsedDeviceMajorClass.fromAndroidIdentifier(majorDeviceClass)

        val icon = iconMapper.getImage(parsedClass, parsedMajorClass)

        return FriendlyClassInfo(
            icon,
            deviceClass,
            majorDeviceClass,
            parsedClass,
            parsedMajorClass
        )
    }
}