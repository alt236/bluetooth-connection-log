package uk.co.alt236.bluetoothconnectionlog.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogDevice
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry

@Dao
interface LogEntryDao {

    @Insert
    fun insert(note: LogEntry)

    @Query("DELETE FROM log_entries")
    fun deleteAllEntries()

    @Query("SELECT * FROM log_entries ")
    fun getAllEntries(): LiveData<List<LogEntry>>

    @Query("SELECT MAX(timestamp) as timestamp, device_mac_address, device_bluetooth_class, device_name, device_type FROM log_entries GROUP BY device_mac_address ORDER BY timestamp DESC")
    fun getAllDevices(): LiveData<List<LogDevice>>

    @Query("SELECT * FROM log_entries WHERE device_mac_address = :macAddress ORDER BY timestamp DESC LIMIT :limit")
    fun getLogForDevice(macAddress: String, limit: Long): LiveData<List<LogEntry>>
}