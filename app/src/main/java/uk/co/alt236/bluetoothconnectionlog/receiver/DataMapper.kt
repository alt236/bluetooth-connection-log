package uk.co.alt236.bluetoothconnectionlog.receiver

import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import android.os.Build
import uk.co.alt236.bluetoothconnectionlog.db.entities.DeviceClass
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
            device_class = deviceClassToEnum(device.bluetoothClass.deviceClass),
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

    private fun deviceClassToEnum(majorClass: Int): DeviceClass {
        return when (majorClass) {
            BluetoothClass.Device.COMPUTER_UNCATEGORIZED -> DeviceClass.COMPUTER_UNCATEGORIZED
            BluetoothClass.Device.COMPUTER_DESKTOP -> DeviceClass.COMPUTER_DESKTOP
            BluetoothClass.Device.COMPUTER_SERVER -> DeviceClass.COMPUTER_SERVER
            BluetoothClass.Device.COMPUTER_LAPTOP -> DeviceClass.COMPUTER_LAPTOP
            BluetoothClass.Device.COMPUTER_HANDHELD_PC_PDA -> DeviceClass.COMPUTER_HANDHELD_PC_PDA
            BluetoothClass.Device.COMPUTER_PALM_SIZE_PC_PDA -> DeviceClass.COMPUTER_PALM_SIZE_PC_PDA
            BluetoothClass.Device.COMPUTER_WEARABLE -> DeviceClass.COMPUTER_WEARABLE

            BluetoothClass.Device.PHONE_UNCATEGORIZED -> DeviceClass.PHONE_UNCATEGORIZED
            BluetoothClass.Device.PHONE_CELLULAR -> DeviceClass.PHONE_CELLULAR
            BluetoothClass.Device.PHONE_CORDLESS -> DeviceClass.PHONE_CORDLESS
            BluetoothClass.Device.PHONE_SMART -> DeviceClass.PHONE_SMART
            BluetoothClass.Device.PHONE_MODEM_OR_GATEWAY -> DeviceClass.PHONE_MODEM_OR_GATEWAY
            BluetoothClass.Device.PHONE_ISDN -> DeviceClass.PHONE_ISDN

            BluetoothClass.Device.AUDIO_VIDEO_UNCATEGORIZED -> DeviceClass.AUDIO_VIDEO_UNCATEGORIZED
            BluetoothClass.Device.AUDIO_VIDEO_WEARABLE_HEADSET -> DeviceClass.AUDIO_VIDEO_WEARABLE_HEADSET
            BluetoothClass.Device.AUDIO_VIDEO_HANDSFREE -> DeviceClass.AUDIO_VIDEO_HANDSFREE
            BluetoothClass.Device.AUDIO_VIDEO_MICROPHONE -> DeviceClass.AUDIO_VIDEO_MICROPHONE
            BluetoothClass.Device.AUDIO_VIDEO_LOUDSPEAKER -> DeviceClass.AUDIO_VIDEO_LOUDSPEAKER
            BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES -> DeviceClass.AUDIO_VIDEO_HEADPHONES
            BluetoothClass.Device.AUDIO_VIDEO_PORTABLE_AUDIO -> DeviceClass.AUDIO_VIDEO_PORTABLE_AUDIO
            BluetoothClass.Device.AUDIO_VIDEO_CAR_AUDIO -> DeviceClass.AUDIO_VIDEO_CAR_AUDIO
            BluetoothClass.Device.AUDIO_VIDEO_SET_TOP_BOX -> DeviceClass.AUDIO_VIDEO_SET_TOP_BOX
            BluetoothClass.Device.AUDIO_VIDEO_HIFI_AUDIO -> DeviceClass.AUDIO_VIDEO_HIFI_AUDIO
            BluetoothClass.Device.AUDIO_VIDEO_VCR -> DeviceClass.AUDIO_VIDEO_VCR
            BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CAMERA -> DeviceClass.AUDIO_VIDEO_VIDEO_CAMERA
            BluetoothClass.Device.AUDIO_VIDEO_CAMCORDER -> DeviceClass.AUDIO_VIDEO_CAMCORDER
            BluetoothClass.Device.AUDIO_VIDEO_VIDEO_MONITOR -> DeviceClass.AUDIO_VIDEO_VIDEO_MONITOR
            BluetoothClass.Device.AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER -> DeviceClass.AUDIO_VIDEO_VIDEO_DISPLAY_AND_LOUDSPEAKER
            BluetoothClass.Device.AUDIO_VIDEO_VIDEO_CONFERENCING -> DeviceClass.AUDIO_VIDEO_VIDEO_CONFERENCING
            BluetoothClass.Device.AUDIO_VIDEO_VIDEO_GAMING_TOY -> DeviceClass.AUDIO_VIDEO_VIDEO_GAMING_TOY

            BluetoothClass.Device.WEARABLE_UNCATEGORIZED -> DeviceClass.WEARABLE_UNCATEGORIZED
            BluetoothClass.Device.WEARABLE_WRIST_WATCH -> DeviceClass.WEARABLE_WRIST_WATCH
            BluetoothClass.Device.WEARABLE_PAGER -> DeviceClass.WEARABLE_PAGER
            BluetoothClass.Device.WEARABLE_JACKET -> DeviceClass.WEARABLE_JACKET
            BluetoothClass.Device.WEARABLE_HELMET -> DeviceClass.WEARABLE_HELMET
            BluetoothClass.Device.WEARABLE_GLASSES -> DeviceClass.WEARABLE_GLASSES

            BluetoothClass.Device.TOY_UNCATEGORIZED -> DeviceClass.TOY_UNCATEGORIZED
            BluetoothClass.Device.TOY_ROBOT -> DeviceClass.TOY_ROBOT
            BluetoothClass.Device.TOY_VEHICLE -> DeviceClass.TOY_VEHICLE
            BluetoothClass.Device.TOY_DOLL_ACTION_FIGURE -> DeviceClass.TOY_DOLL_ACTION_FIGURE
            BluetoothClass.Device.TOY_CONTROLLER -> DeviceClass.TOY_CONTROLLER
            BluetoothClass.Device.TOY_GAME -> DeviceClass.TOY_GAME

            BluetoothClass.Device.HEALTH_UNCATEGORIZED -> DeviceClass.HEALTH_UNCATEGORIZED
            BluetoothClass.Device.HEALTH_BLOOD_PRESSURE -> DeviceClass.HEALTH_BLOOD_PRESSURE
            BluetoothClass.Device.HEALTH_THERMOMETER -> DeviceClass.HEALTH_THERMOMETER
            BluetoothClass.Device.HEALTH_WEIGHING -> DeviceClass.HEALTH_WEIGHING
            BluetoothClass.Device.HEALTH_GLUCOSE -> DeviceClass.HEALTH_GLUCOSE
            BluetoothClass.Device.HEALTH_PULSE_OXIMETER -> DeviceClass.HEALTH_PULSE_OXIMETER
            BluetoothClass.Device.HEALTH_PULSE_RATE -> DeviceClass.HEALTH_PULSE_RATE
            BluetoothClass.Device.HEALTH_DATA_DISPLAY -> DeviceClass.HEALTH_DATA_DISPLAY
            else -> DeviceClass.UNKNOWN
        }
    }
}