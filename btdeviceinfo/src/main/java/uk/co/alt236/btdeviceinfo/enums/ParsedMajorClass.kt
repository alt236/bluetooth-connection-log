package uk.co.alt236.btdeviceinfo.enums

import android.bluetooth.BluetoothClass

enum class ParsedDeviceMajorClass(val androidIdentifier: Int) {
    UNKNOWN(-1),
    MISC(BluetoothClass.Device.Major.MISC),
    COMPUTER(BluetoothClass.Device.Major.COMPUTER),
    PHONE(BluetoothClass.Device.Major.PHONE),
    NETWORKING(BluetoothClass.Device.Major.NETWORKING),
    AUDIO_VIDEO(BluetoothClass.Device.Major.AUDIO_VIDEO),
    PERIPHERAL(BluetoothClass.Device.Major.PERIPHERAL),
    IMAGING(BluetoothClass.Device.Major.IMAGING),
    WEARABLE(BluetoothClass.Device.Major.WEARABLE),
    TOY(BluetoothClass.Device.Major.TOY),
    HEALTH(BluetoothClass.Device.Major.HEALTH),
    UNCATEGORIZED(BluetoothClass.Device.Major.UNCATEGORIZED);

    companion object {
        internal fun fromAndroidIdentifier(identifier: Int): ParsedDeviceMajorClass {
            for (deviceClass in values()) {
                if (identifier == deviceClass.androidIdentifier) {
                    return deviceClass
                }
            }
            return UNKNOWN
        }
    }
}