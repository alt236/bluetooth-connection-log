package uk.co.alt236.bluetoothconnectionlog.ui.detail.recycler

import android.view.View
import androidx.fragment.app.FragmentActivity
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry
import uk.co.alt236.bluetoothconnectionlog.ui.navigation.Navigator

internal class ViewHolderBinder(
    activity: FragmentActivity,
    private val navigator: Navigator
) {

    private val dateFormatter = DateFormatter(activity)

    private val onClickListener = View.OnClickListener { v ->
        val item = v.tag as LogEntry

        if (item.location.valid) {
            navigator.openMap(item.location)
        }
    }

    fun onBindViewHolder(holder: ViewHolder, item: LogEntry) {
        holder.action.text = item.event.toString()
        holder.timestamp.text = dateFormatter.format(item.timestamp)

        if (item.location.valid) {
            holder.image.visibility = View.VISIBLE
        } else {
            holder.image.visibility = View.GONE
        }

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }
}