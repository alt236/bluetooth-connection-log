package uk.co.alt236.bluetoothconnectionlog.ui.detail.recycler

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry
import uk.co.alt236.bluetoothconnectionlog.ui.navigation.MapIntentFactory
import java.util.*

class ViewHolderBinder(private val activity: FragmentActivity) {
    private val mapIntentFactory = MapIntentFactory(activity)

    private val onClickListener = View.OnClickListener { v ->
        val item = v.tag as LogEntry

        if (item.location.valid) {
            val intent = mapIntentFactory.createIntent(item.location)

            if (intent.resolveActivity(activity.packageManager) != null) {
                ContextCompat.startActivity(activity, intent, null)
            }
        }
    }

    fun onBindViewHolder(holder: ViewHolder, item: LogEntry) {
        holder.action.text = item.event.toString()
        holder.timestamp.text = Date(item.timestamp).toString()

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