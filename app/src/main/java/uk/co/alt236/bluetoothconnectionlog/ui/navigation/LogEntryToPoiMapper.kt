package uk.co.alt236.bluetoothconnectionlog.ui.navigation

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.db.entities.Event
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry
import uk.co.alt236.bluetoothconnectionlog.map.model.Graphics
import uk.co.alt236.bluetoothconnectionlog.map.model.Poi

class LogEntryToPoiMapper {

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
            Event.CONNECTED -> CONNECTED_COLOR
            Event.DISCONNECTED -> DISCONNECTED_COLOR
            Event.DISCONNECT_REQUESTED -> DISCONNECT_REQ_COLOR
            else -> UNKNOWN_COLOR
        }
    }

    private companion object {
        val CONNECTED_COLOR = Color.parseColor("#4CAF50")
        val DISCONNECTED_COLOR = Color.parseColor("#FF5222")
        val DISCONNECT_REQ_COLOR = Color.parseColor("#FFC107")
        val UNKNOWN_COLOR = Color.parseColor("#1B50BB")
    }
}