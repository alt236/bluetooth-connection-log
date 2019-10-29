package uk.co.alt236.bluetoothconnectionlog.repo.personalisedlog

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import uk.co.alt236.bluetoothconnectionlog.db.entities.BtDevice
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogDevice
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry
import uk.co.alt236.bluetoothconnectionlog.repo.favourites.FavouritesRepository
import uk.co.alt236.bluetoothconnectionlog.repo.log.LogRepository
import java.util.*
import kotlin.collections.ArrayList

class PersonalisedLogRepository(context: Context) {
    private val favRepo =
        FavouritesRepository(context)
    private val logRepo = LogRepository(context)

    fun getAllDevices(): LiveData<List<PersonalisedLogDevice>> {
        return CombinedLiveData(
            logRepo.getAllDevices(),
            favRepo.getAllFavourites()
        ) { data1, data2 -> combine(data1, data2) }
    }

    fun getLogForDevice(device: BtDevice): LiveData<List<LogEntry>> {
        return logRepo.getLogForDevice(device)
    }

    fun getFavStatusForDevice(device: BtDevice): LiveData<Boolean> {
        return Transformations.map(favRepo.getAllFavourites()) { favRepo.isFavourite(device) }
    }

    fun addToFavourites(device: BtDevice) {
        favRepo.addToFavourites(device)
    }

    fun removeFromFavourites(device: BtDevice) {
        favRepo.removeFromFavourites(device)
    }

    private fun combine(
        devices: List<LogDevice>?,
        favs: Set<String>?
    ): List<PersonalisedLogDevice> {
        return if (devices == null || favs == null) {
            emptyList()
        } else {
            val retVal = ArrayList<PersonalisedLogDevice>(devices.size)
            for (device in devices) {
                retVal.add(
                    PersonalisedLogDevice(
                        device,
                        favRepo.isFavourite(device.device)
                    )
                )
            }

            Collections.unmodifiableList(retVal)
        }
    }

    private class CombinedLiveData<T, K, S>(
        source1: LiveData<T>,
        source2: LiveData<K>,
        private val combine: (data1: T?, data2: K?) -> S
    ) : MediatorLiveData<S>() {

        private var data1: T? = null
        private var data2: K? = null

        init {
            super.addSource(source1) {
                data1 = it
                value = combine(data1, data2)
            }
            super.addSource(source2) {
                data2 = it
                value = combine(data1, data2)
            }
        }

        override fun <S : Any?> addSource(source: LiveData<S>, onChanged: Observer<in S>) {
            throw UnsupportedOperationException()
        }

        override fun <T : Any?> removeSource(toRemote: LiveData<T>) {
            throw UnsupportedOperationException()
        }
    }

}