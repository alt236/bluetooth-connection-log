package uk.co.alt236.bluetoothconnectionlog.db.entities

import androidx.room.ColumnInfo

class Location(
    @ColumnInfo(name = "valid")
    val valid: Boolean = true,

    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "altitude")
    val altitude: Double,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long,
    @ColumnInfo(name = "accuracy_horizontal")
    val horizontalAccuracy: Float,
    @ColumnInfo(name = "accuracy_vertical")
    val verticalAccuracy: Float
) {
    companion object {
        fun createInvalid(): Location {
            return Location(
                valid = false,
                latitude = 0.0,
                longitude = 0.0,
                altitude = 0.0,
                timestamp = 0,
                horizontalAccuracy = 0.0f,
                verticalAccuracy = 0.0f
            )
        }
    }
}