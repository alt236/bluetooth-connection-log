package uk.co.alt236.bluetoothconnectionlog.db.entities

import androidx.room.ColumnInfo

data class LogDevice(

    @ColumnInfo(name = "device_class")
    val device_class: BluetoothClass,

    @ColumnInfo(name = "device_mac_address")
    val mac_address: String,

    @ColumnInfo(name = "device_name")
    val device_name: String,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long
)