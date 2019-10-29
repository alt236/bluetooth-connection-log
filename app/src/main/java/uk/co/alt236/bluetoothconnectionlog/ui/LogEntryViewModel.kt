package uk.co.alt236.bluetoothconnectionlog.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import uk.co.alt236.bluetoothconnectionlog.db.entities.BtDevice
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry
import uk.co.alt236.bluetoothconnectionlog.repo.personalisedlog.PersonalisedLogDevice
import uk.co.alt236.bluetoothconnectionlog.repo.personalisedlog.PersonalisedLogRepository

class LogEntryViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = PersonalisedLogRepository(application)

    fun getAllDevices(): LiveData<List<PersonalisedLogDevice>> {
        return repo.getAllDevices()
    }

    fun getLogForDevice(device: BtDevice): LiveData<List<LogEntry>> {
        return repo.getLogForDevice(device)
    }

    fun getFavStatusForDevice(device: BtDevice): LiveData<Boolean> {
        return repo.getFavStatusForDevice(device)
    }

    fun addToFavourites(device: BtDevice) {
        repo.addToFavourites(device)
    }

    fun removeFromFavourites(device: BtDevice) {
        repo.removeFromFavourites(device)
    }
}