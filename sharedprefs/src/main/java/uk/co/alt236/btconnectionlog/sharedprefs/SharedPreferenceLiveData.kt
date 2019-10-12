package uk.co.alt236.btconnectionlog.sharedprefs


import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import uk.co.alt236.btconnectionlog.sharedprefs.keymatcher.AnyKeyMatcher
import uk.co.alt236.btconnectionlog.sharedprefs.keymatcher.KeyMatcher
import uk.co.alt236.btconnectionlog.sharedprefs.keymatcher.SpecificKeyMatcher

abstract class SharedPreferenceLiveData<T>(
    internal val sharedPrefs: SharedPreferences,
    private val keyMatcher: KeyMatcher,
    private val defValue: T
) : LiveData<T>() {

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (keyMatcher.matches(sharedPrefs, key)) {
                value = getValueFromPreferences(key, defValue)
            }
        }

    internal abstract fun getValueFromPreferences(key: String, defValue: T): T

    override fun onActive() {
        super.onActive()
        value = getValueInternal()
        sharedPrefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }

    private fun getValueInternal(): T {
        return when (keyMatcher) {
            is SpecificKeyMatcher -> getValueFromPreferences(keyMatcher.key, defValue)
            is AnyKeyMatcher -> getValueFromPreferences(AnyKeyMatcher.ANY_KEY, defValue)
            else -> throw IllegalArgumentException("Unknown key matcher: ${keyMatcher::class.java}/$keyMatcher")
        }
    }
}