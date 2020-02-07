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

        for (deviceNo in 0 until NUMBER_OF_DEVICES) {
            val macAddress = "AA:FF:FF:FF:FF:F$deviceNo"
            val btClass = BluetoothClass(
                BluetoothClass.Device.PHONE_SMART,
                BluetoothClass.Device.Major.PHONE
            )
            val btDevice = BtDevice(btClass, macAddress, "Device #$deviceNo", DeviceType.DUAL)
            createEntries(deviceNo, btDevice)
        }

        prefs.edit().putBoolean(KEY_ALREADY_ADDED, true).apply()
    }


    private fun createEntries(deviceNo: Int, btDevice: BtDevice) {
        for (eventNo in 0 until NUMBER_OF_ENTRIES) {
            val baseTimestamp = LOCATIONS[deviceNo].timestamp
            val timestamp = baseTimestamp + (eventNo * 1000)

            val location = if (eventNo % 9 == 0) {
                Location.INVALID
            } else {
                LOCATIONS[deviceNo]
            }

            val event = when {
                (eventNo % 7 == 0) -> Event.UNKNOWN
                (eventNo % 2 == 0) -> Event.CONNECTED
                (eventNo % 5 == 0) -> Event.DISCONNECTED
                (eventNo % 3 == 0) -> Event.DISCONNECT_REQUESTED
                else -> Event.CONNECTED
            }

            val logEntry = LogEntry(event, timestamp, btDevice, location)
            repo.insert(logEntry)
        }
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


        val NUMBER_OF_DEVICES = LOCATIONS.size
        const val NUMBER_OF_ENTRIES = 100
    }
}