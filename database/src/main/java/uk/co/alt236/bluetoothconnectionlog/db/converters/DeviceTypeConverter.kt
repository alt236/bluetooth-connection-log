package uk.co.alt236.bluetoothconnectionlog.db.converters

import androidx.room.TypeConverter
import uk.co.alt236.bluetoothconnectionlog.db.entities.DeviceType

class DeviceTypeConverter {
    @TypeConverter
    fun typeToEnum(value: Int?): DeviceType {
        return if (value == null) {
            DeviceType.UNKNOWN
        } else {
            return DeviceType.fromAndroidType(value)
        }
    }

    @TypeConverter
    fun enumToType(value: DeviceType): Int {
        return value.androidType
    }
}