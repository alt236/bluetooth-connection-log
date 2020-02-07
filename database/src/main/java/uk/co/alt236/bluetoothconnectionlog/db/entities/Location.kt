package uk.co.alt236.bluetoothconnectionlog.db.entities

import androidx.room.ColumnInfo
import java.io.Serializable

class Location(
    @ColumnInfo(name = "valid")
    internal val valid: Boolean = true,

    @ColumnInfo(name = "latitude")
    val latitude: Double,
    @ColumnInfo(name = "longitude")
    val longitude: Double,
    @ColumnInfo(name = "altitude")
    val altitude: Double,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long,
    @ColumnInfo(name = "accuracy_horizontal")
    val horizontalAccuracy: Float = 0.0f,
    @ColumnInfo(name = "accuracy_vertical")
    val verticalAccuracy: Float = 0.0f
) : Serializable {

    fun isValid(): Boolean {
        return valid
                && timestamp != 0L // This is to fix a bug where the valid flag in INVALID was not set to false
    }

    companion object {

        @JvmField
        val INVALID = Location(
            valid = false,
            latitude = 0.0,
            longitude = 0.0,
            altitude = 0.0,
            timestamp = 0
        )
    }
}