package uk.co.alt236.bluetoothconnectionlog.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogDevice
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry
import uk.co.alt236.bluetoothconnectionlog.repo.LogRepository

class LogEntryViewModel(application: Application) : AndroidViewModel(application) {
    private val logRepo: LogRepository = LogRepository(application)

    fun getAllDevices(): LiveData<List<LogDevice>> {
        return logRepo.getAllDevices()
    }

    fun getLogForDevice(macAdress: String): LiveData<List<LogEntry>> {
        return logRepo.getLogForDevice(macAdress)
    }
}