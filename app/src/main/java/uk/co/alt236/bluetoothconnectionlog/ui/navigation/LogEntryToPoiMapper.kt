package uk.co.alt236.bluetoothconnectionlog.ui.navigation

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.db.entities.Event
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry
import uk.co.alt236.bluetoothconnectionlog.map.model.Graphics
import uk.co.alt236.bluetoothconnectionlog.map.model.Poi

class LogEntryToPoiMapper(private val context: Context) {

    fun map(entry: LogEntry): Poi {
        val graphics = Graphics(
            accuracyCircleColor = getColor(entry.event),
            markerDrawableId = getIcon(entry.event)
        )

        return Poi(
            entry.getDisplayName(),
            entry.location.latitude,
            entry.location.longitude,
            entry.location.horizontalAccuracy,
            graphics
        )
    }

    @DrawableRes
    fun getIcon(event: Event): Int {
        return when (event) {
            Event.CONNECTED -> R.drawable.ic_map_pin_device_connected
            Event.DISCONNECTED -> R.drawable.ic_map_pin_device_disconnected
            Event.DISCONNECT_REQUESTED -> R.drawable.ic_map_pin_device_disconnect_requested
            else -> R.drawable.ic_map_pin_device_unknown_event
        }
    }

    @ColorInt
    fun getColor(event: Event): Int {
        return when (event) {
            Event.CONNECTED -> ContextCompat.getColor(context, R.color.bt_action_connected)
            Event.DISCONNECTED -> ContextCompat.getColor(context, R.color.bt_action_disconnected)
            Event.DISCONNECT_REQUESTED -> ContextCompat.getColor(
                context,
                R.color.bt_action_disconnect_requested
            )
            else -> ContextCompat.getColor(context, R.color.bt_action_unknown)
        }
    }
}