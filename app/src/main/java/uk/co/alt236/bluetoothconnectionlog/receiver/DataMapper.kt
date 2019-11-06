package uk.co.alt236.bluetoothconnectionlog.receiver

import android.bluetooth.BluetoothDevice
import android.os.Build
import uk.co.alt236.bluetoothconnectionlog.db.entities.*

class DataMapper {

    fun createLogEntry(
        device: BluetoothDevice,
        event: String,
        timestamp: Long
    ): LogEntry {
        val btDevice = BtDevice(
            name = device.name ?: "",
            macAddress = device.address,
            bluetoothClass = BluetoothClass(device.bluetoothClass)
        )

        return LogEntry(
            timestamp = timestamp,
            event = bluetoothConnectionEventToAEnum(event),
            location = Location.INVALID,
            device = btDevice
        )
    }

    fun updateWithLocation(
        logEntry: LogEntry,
        androidLocation: android.location.Location
    ): LogEntry {
        val location = Location(
            latitude = androidLocation.latitude,
            longitude = androidLocation.longitude,
            altitude = androidLocation.altitude,

            timestamp = androidLocation.time,
            horizontalAccuracy = androidLocation.accuracy,
            verticalAccuracy = getVerticalAccuracy(androidLocation)
        )

        return LogEntry(
            timestamp = logEntry.timestamp,
            event = logEntry.event,
            location = location,
            device = logEntry.device
        )
    }

    private fun getVerticalAccuracy(androidLocation: android.location.Location): Float {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            androidLocation.verticalAccuracyMeters
        } else {
            0.0f
        }
    }

    private fun bluetoothConnectionEventToAEnum(event: String): Event {
        return when (event) {
            BluetoothDevice.ACTION_ACL_CONNECTED -> Event.CONNECTED
            BluetoothDevice.ACTION_ACL_DISCONNECTED -> Event.DISCONNECTED
            BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED -> Event.DISCONNECT_REQUESTED
            else -> Event.UNKNOWN
        }
    }
}