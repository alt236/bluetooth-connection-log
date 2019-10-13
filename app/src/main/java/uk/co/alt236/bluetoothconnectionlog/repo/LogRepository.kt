package uk.co.alt236.bluetoothconnectionlog.repo

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import uk.co.alt236.bluetoothconnectionlog.BuildConfig
import uk.co.alt236.bluetoothconnectionlog.db.LogDatabase
import uk.co.alt236.bluetoothconnectionlog.db.LogEntryDao
import uk.co.alt236.bluetoothconnectionlog.db.entities.BtDevice
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogDevice
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry

class LogRepository(context: Context) {
    private val dummyDataInserter: DummyDataInserter = DummyDataInserter(context, this)
    private var logDao: LogEntryDao

    init {
        val database: LogDatabase = LogDatabase.getInstance(context.applicationContext)
        logDao = database.entryDao()
        insertDummyData()
    }

    fun insert(note: LogEntry) {
        InsertAsyncTask(logDao).execute(note)
    }

    fun getAllEntries(): LiveData<List<LogEntry>> {
        return logDao.getAllEntries()
    }

    fun getAllDevices(): LiveData<List<LogDevice>> {
        return logDao.getAllDevices()
    }

    fun getLogForDevice(device: LogDevice): LiveData<List<LogEntry>> {
        return getLogForDevice(device.device)
    }

    fun getLogForDevice(device: BtDevice): LiveData<List<LogEntry>> {
        return getLogForDevice(device.macAddress)
    }

    fun getLogForDevice(macAddress: String): LiveData<List<LogEntry>> {
        return logDao.getLogForDevice(macAddress)
    }

    private class InsertAsyncTask(private val logDao: LogEntryDao) :
        AsyncTask<LogEntry, Void, Void>() {
        override fun doInBackground(vararg logEntries: LogEntry): Void? {
            logDao.insert(logEntries[0])
            return null
        }
    }

    private fun insertDummyData() {
        if (BuildConfig.DEBUG && BuildConfig.INSERT_DUMMY_DATA) {
            dummyDataInserter.insertDummyData()
        }
    }
}