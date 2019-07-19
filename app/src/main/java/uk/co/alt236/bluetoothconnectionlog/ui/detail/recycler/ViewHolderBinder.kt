package uk.co.alt236.bluetoothconnectionlog.ui.detail.recycler

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry
import java.util.*

class ViewHolderBinder(private val activity: FragmentActivity) {

    private val onClickListener = View.OnClickListener { v ->
        val item = v.tag as LogEntry

        if (item.location.valid) {
            val gmmIntentUri = Uri.parse("geo:${item.location.latitude},${item.location.longitude}?z=15")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(activity.packageManager) != null) {
                ContextCompat.startActivity(activity, mapIntent, null)
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