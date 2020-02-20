package uk.co.alt236.bluetoothconnectionlog.ui.detail.recycler

import android.view.View
import androidx.fragment.app.FragmentActivity
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.db.entities.Event
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry
import uk.co.alt236.bluetoothconnectionlog.ui.navigation.Navigator

internal class ViewHolderBinder(
    activity: FragmentActivity,
    private val navigator: Navigator
) {

    private val dateFormatter = DateFormatter(activity)

    private val onClickListener = View.OnClickListener { v ->
        val item = v.tag as LogEntry

        if (item.location.isValid()) {
            navigator.openMap(item)
        }
    }

    fun onBindViewHolder(holder: ViewHolder, item: LogEntry) {
        val actionDescription = getActionDescription(item)

        showLocation(holder, item)
        holder.setActionInfo(actionDescription)
        holder.setTimeStamp(dateFormatter.format(item.timestamp))
    }

    private fun showLocation(holder: ViewHolder, item: LogEntry) {
        val hasLocation = item.location.isValid()
        holder.showLocationButton(hasLocation)

        with(holder.itemView) {
            tag = item
            if (hasLocation) {
                setOnClickListener(onClickListener)
            } else {
                setOnClickListener(null)
            }
            isClickable = hasLocation
        }
    }

    private fun getActionDescription(item: LogEntry): ViewHolder.ActionDescription {
        return when (item.event) {
            Event.UNKNOWN -> ViewHolder.ActionDescription(
                R.color.bt_action_unknown,
                R.drawable.ic_bluetooth_unknown_white_24dp,
                R.drawable.ic_action_marker_unknown,
                item.event.toString()
            )
            Event.CONNECTED -> ViewHolder.ActionDescription(
                R.color.bt_action_connected,
                R.drawable.ic_bluetooth_connected_white_24dp,
                R.drawable.ic_action_marker_connected,
                item.event.toString()
            )
            Event.DISCONNECT_REQUESTED -> ViewHolder.ActionDescription(
                R.color.bt_action_disconnect_requested,
                R.drawable.ic_bluetooth_disabled_white_24dp,
                R.drawable.ic_action_marker_disconnect_requested,
                item.event.toString()
            )
            Event.DISCONNECTED -> ViewHolder.ActionDescription(
                R.color.bt_action_disconnected,
                R.drawable.ic_bluetooth_disabled_white_24dp,
                R.drawable.ic_action_marker_disconnected,
                item.event.toString()
            )
        }
    }
}