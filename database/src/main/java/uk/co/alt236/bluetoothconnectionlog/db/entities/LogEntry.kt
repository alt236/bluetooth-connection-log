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

    @ColumnInfo(name = "device_mac_address")
    val mac_address: String,

    @ColumnInfo(name = "device_name")
    val device_name: String,

    @Embedded(prefix = "location_")
    val location: Location
) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}