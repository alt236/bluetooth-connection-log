package uk.co.alt236.bluetoothconnectionlog.db.converters

import androidx.room.TypeConverter
import uk.co.alt236.bluetoothconnectionlog.db.entities.DeviceClass

class DeviceClassTypeConverter {
    @TypeConverter
    fun classStringToEnum(value: String?): DeviceClass {
        return if (value == null) {
            DeviceClass.UNKNOWN
        } else {
            for (enum in DeviceClass.values()) {
                if (enum.name == value) {
                    return enum
                }
            }
            return DeviceClass.UNKNOWN
        }
    }

    @TypeConverter
    fun classEnumToString(value: DeviceClass): String {
        return value.name
    }
}