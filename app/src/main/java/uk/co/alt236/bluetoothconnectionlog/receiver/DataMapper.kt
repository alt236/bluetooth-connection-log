package uk.co.alt236.bluetoothconnectionlog.receiver

import android.bluetooth.BluetoothDevice
import android.os.Build
import uk.co.alt236.bluetoothconnectionlog.db.entities.BluetoothClass
import uk.co.alt236.bluetoothconnectionlog.db.entities.Event
import uk.co.alt236.bluetoothconnectionlog.db.entities.Location
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry

class DataMapper {

    fun createLogEntry(
        device: BluetoothDevice,
        event: String,
        timestamp: Long
    ): LogEntry {
        return LogEntry(
            device_name = device.name,
            mac_address = device.address,
            timestamp = timestamp,
            event = bluetoothConnectionEventToAEnum(event),
            device_class = BluetoothClass(device.bluetoothClass),
            location = Location.createInvalid()
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
            device_name = logEntry.device_name,
            mac_address = logEntry.mac_address,
            timestamp = logEntry.timestamp,
            event = logEntry.event,
            device_class = logEntry.device_class,
            location = location
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