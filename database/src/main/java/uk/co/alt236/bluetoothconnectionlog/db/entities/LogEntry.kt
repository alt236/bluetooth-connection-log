package uk.co.alt236.bluetoothconnectionlog.db.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

const val TABLE_NAME = "log_entries"

@Entity(tableName = TABLE_NAME)
data class LogEntry(
    @ColumnInfo(name = "event")
    val event: Event,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long,

    @Embedded(prefix = "device_")
    val device: BtDevice,

    @Embedded(prefix = "location_")
    val location: Location
) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    fun getDisplayName(): String {
        return device.getDisplayName()
    }
}