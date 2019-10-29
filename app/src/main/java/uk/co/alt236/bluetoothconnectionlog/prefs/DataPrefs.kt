package uk.co.alt236.bluetoothconnectionlog.prefs

import android.content.Context
import androidx.preference.PreferenceManager
import uk.co.alt236.bluetoothconnectionlog.R

internal class DataPrefs(private val context: Context) {
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    fun getEventRowsToDisplay(): Long {
        val key = context.getString(R.string.preference_key_event_rows_to_display)
        val default = context.getString(R.string.preference_event_rows_to_display_default_value)
        return prefs.getString(key, default)!!.toLong()
    }
}