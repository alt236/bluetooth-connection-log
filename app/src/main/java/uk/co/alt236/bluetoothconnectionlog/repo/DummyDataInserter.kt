package uk.co.alt236.bluetoothconnectionlog.repo

import android.bluetooth.BluetoothClass
import android.content.Context
import android.content.Context.MODE_PRIVATE
import uk.co.alt236.bluetoothconnectionlog.BuildConfig
import uk.co.alt236.bluetoothconnectionlog.db.entities.BtDevice
import uk.co.alt236.bluetoothconnectionlog.db.entities.Event
import uk.co.alt236.bluetoothconnectionlog.db.entities.Location
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry

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
            val btClass = uk.co.alt236.bluetoothconnectionlog.db.entities.BluetoothClass(
                BluetoothClass.Device.PHONE_SMART,
                BluetoothClass.Device.Major.PHONE
            )
            val macAddress = "AA:FF:FF:FF:FF:F$x"
            val btDevice = BtDevice(btClass, macAddress, "Device #$x")

            val location = LOCATIONS[x]
            val logEntry = LogEntry(Event.CONNECTED, location.timestamp, btDevice, location)

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
                timestamp = 1570964656000
            ), // Athens
            Location(
                latitude = 30.05,
                longitude = 31.25,
                altitude = 0.0,
                timestamp = 1570878256000
            ), //Cairo
            Location(
                latitude = 37.98333333,
                longitude = 23.733333,
                altitude = 0.0,
                timestamp = 1570791856000
            ) //Rome
        )
        val NUMBER_OF_ITEMS = LOCATIONS.size
    }
}