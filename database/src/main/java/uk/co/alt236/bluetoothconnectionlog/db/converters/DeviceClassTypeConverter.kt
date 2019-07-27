package uk.co.alt236.bluetoothconnectionlog.db.converters

import androidx.room.TypeConverter
import uk.co.alt236.bluetoothconnectionlog.db.entities.BluetoothClass
import java.util.*

private const val STRING_FORMAT = "%d|%d"

class DeviceClassTypeConverter {
    @TypeConverter
    fun stringToObject(value: String): BluetoothClass {
        val array = value.split("|")
        val deviceClass = Integer.valueOf(array[0])
        val majorDeviceClass = Integer.valueOf(array[1])

        return BluetoothClass(deviceClass = deviceClass, majorDeviceClass = majorDeviceClass)
    }

    @TypeConverter
    fun objectToString(value: BluetoothClass): String {
        return String.format(Locale.US, STRING_FORMAT, value.deviceClass, value.majorDeviceClass)
    }
}