package uk.co.alt236.bluetoothconnectionlog.db.entities

import androidx.room.ColumnInfo
import java.io.Serializable

data class BtDevice(
    @ColumnInfo(name = "bluetooth_class")
    val bluetoothClass: BluetoothClass,

    @ColumnInfo(name = "mac_address")
    val macAddress: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "type")
    val type: DeviceType

) : Serializable {

    fun getDisplayName(): String {
        val trimmedName = name.trim()
        return if (trimmedName.isBlank()) macAddress else trimmedName
    }
}