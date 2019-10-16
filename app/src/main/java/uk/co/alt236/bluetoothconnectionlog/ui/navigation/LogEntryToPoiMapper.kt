package uk.co.alt236.bluetoothconnectionlog.ui.navigation

import androidx.annotation.DrawableRes
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.db.entities.Event
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry
import uk.co.alt236.bluetoothconnectionlog.map.Poi

class LogEntryToPoiMapper {

    fun map(entry: LogEntry): Poi {

        return Poi(
            entry.getDisplayName(),
            entry.location.latitude,
            entry.location.longitude,
            entry.location.horizontalAccuracy,
            getIcon(entry.event)
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
}