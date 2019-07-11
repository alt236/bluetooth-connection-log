package uk.co.alt236.bluetoothconnectionlog.db.converters

import androidx.room.TypeConverter
import uk.co.alt236.bluetoothconnectionlog.db.entities.Event


class EventTypeConverter {
    @TypeConverter
    fun eventStringToEnum(value: String?): Event {
        return if (value == null) {
            Event.UNKNOWN
        } else {
            for (enum in Event.values()) {
                if (enum.name == value) {
                    return enum
                }
            }
            return Event.UNKNOWN
        }
    }

    @TypeConverter
    fun eventEnumToString(value: Event): String {
        return value.name
    }
}