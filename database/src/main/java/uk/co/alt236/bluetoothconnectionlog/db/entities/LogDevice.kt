package uk.co.alt236.bluetoothconnectionlog.db.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded

data class LogDevice(
    @Embedded(prefix = "device_")
    val device: BtDevice,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long
)