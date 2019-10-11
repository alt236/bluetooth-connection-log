package uk.co.alt236.bluetoothconnectionlog.repo

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import uk.co.alt236.bluetoothconnectionlog.db.LogDatabase
import uk.co.alt236.bluetoothconnectionlog.db.LogEntryDao
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogDevice
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry

class LogRepository(context: Context) {

    private var logDao: LogEntryDao

    init {
        val database: LogDatabase = LogDatabase.getInstance(context.applicationContext)
        logDao = database.entryDao()
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

    fun getLogForDevice(macAddress: String): LiveData<List<LogEntry>> {
        return logDao.getLogForDevice(macAddress)
    }

    private class InsertAsyncTask(private val logDao: LogEntryDao) : AsyncTask<LogEntry, Void, Void>() {
        override fun doInBackground(vararg logEntries: LogEntry): Void? {
            logDao.insert(logEntries[0])
            return null
        }
    }
}