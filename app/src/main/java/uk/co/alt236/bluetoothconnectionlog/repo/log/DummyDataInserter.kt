package uk.co.alt236.bluetoothconnectionlog.repo.log

import android.bluetooth.BluetoothClass
import android.content.Context
import android.content.Context.MODE_PRIVATE
import uk.co.alt236.bluetoothconnectionlog.BuildConfig
import uk.co.alt236.bluetoothconnectionlog.db.entities.*

internal class DummyDataInserter(
    context: Context,
    private val repo: LogRepository
) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

    fun insertDummyData() {
        if (prefs.getBoolean(KEY_ALREADY_ADDED, false)) {
            return
        }

        for (x in 0 until NUMBER_OF_ITEMS) {
            val btClass = BluetoothClass(
                BluetoothClass.Device.PHONE_SMART,
                BluetoothClass.Device.Major.PHONE
            )
            val macAddress = "AA:FF:FF:FF:FF:F$x"
            val btDevice = BtDevice(btClass, macAddress, "Device #$x", DeviceType.DUAL)

            val location = LOCATIONS[x]
            val event = when (x) {
                0 -> Event.CONNECTED
                1 -> Event.DISCONNECTED
                2 -> Event.DISCONNECT_REQUESTED
                else -> Event.UNKNOWN
            }

            val logEntry = LogEntry(event, location.timestamp, btDevice, location)

            repo.insert(logEntry)
        }

        prefs.edit().putBoolean(KEY_ALREADY_ADDED, true).apply()
    }

    private companion object {
        const val PREFS_NAME = BuildConfig.APPLICATION_ID + ".DUMMY_DATA_PREFS"
        const val KEY_ALREADY_ADDED = "already_added"

        val LOCATIONS = arrayOf(
            Location(
                latitude = 37.98333333,
                longitude = 23.733333,
                altitude = 0.0,
                timestamp = 1570964656000,
                horizontalAccuracy = 100.0f
            ), // Athens
            Location(
                latitude = 30.05,
                longitude = 31.25,
                altitude = 0.0,
                timestamp = 1570878256000,
                horizontalAccuracy = 100.0f
            ), //Cairo
            Location(
                latitude = 41.890251,
                longitude = 12.492373,
                altitude = 0.0,
                timestamp = 1570791856000,
                horizontalAccuracy = 100.0f
            ), //Rome
            Location(
                latitude = 39.916668,
                longitude = 116.383331,
                altitude = 0.0,
                timestamp = 1570601856000,
                horizontalAccuracy = 100.0f
            ) //Beijing
        )
        val NUMBER_OF_ITEMS = LOCATIONS.size
    }
}