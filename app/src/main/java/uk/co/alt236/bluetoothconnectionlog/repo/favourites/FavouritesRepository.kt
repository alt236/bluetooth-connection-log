package uk.co.alt236.bluetoothconnectionlog.repo.favourites

import android.content.Context
import androidx.lifecycle.LiveData
import uk.co.alt236.bluetoothconnectionlog.BuildConfig
import uk.co.alt236.bluetoothconnectionlog.db.entities.BtDevice
import uk.co.alt236.btconnectionlog.sharedprefs.SharedPreferenceKeysLiveData
import java.util.*

class FavouritesRepository(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val prefKeysLiveData = SharedPreferenceKeysLiveData(prefs)

    fun addToFavourites(device: BtDevice) {
        val repoKey = device.repoKey()
        prefs.edit().putString(repoKey, "").apply()
    }

    fun removeFromFavourites(device: BtDevice) {
        val repoKey = device.repoKey()
        prefs.edit().remove(repoKey).apply()
    }

    fun isFavourite(device: BtDevice): Boolean {
        val repoKey = device.repoKey()
        return prefs.contains(repoKey)
    }

    fun getAllFavourites(): LiveData<Set<String>> {
        return prefKeysLiveData
    }

    private fun BtDevice.repoKey(): String {
        return KEY_REGEX.replace(macAddress.toLowerCase(Locale.US), "")
    }

    private companion object {
        const val PREFS_NAME = "${BuildConfig.APPLICATION_ID}.FAVOURITES"
        val KEY_REGEX = Regex("[^A-Za-z0-9 ]")
    }
}