package uk.co.alt236.bluetoothconnectionlog.receiver

import android.content.Context
import androidx.preference.PreferenceManager
import uk.co.alt236.bluetoothconnectionlog.R

internal class NotificationPrefs(private val context: Context) {
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    fun shouldNotifyOnConnection(): Boolean {
        return prefs.getBoolean(
            context.getString(R.string.preference_key_notify_on_connection),
            false
        )
    }

    fun shouldNotifyOnDisconnection(): Boolean {
        return prefs.getBoolean(
            context.getString(R.string.preference_key_notify_on_disconnection),
            false
        )
    }

    fun shouldNotifyOnDisconnectionRequest(): Boolean {
        return prefs.getBoolean(
            context.getString(R.string.preference_key_notify_on_disconnection_request),
            false
        )
    }
}