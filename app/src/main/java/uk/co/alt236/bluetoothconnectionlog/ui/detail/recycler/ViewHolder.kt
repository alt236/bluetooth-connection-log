package uk.co.alt236.bluetoothconnectionlog.ui.detail.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uk.co.alt236.bluetoothconnectionlog.R

class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
    val action: TextView = root.findViewById(R.id.action)
    val timestamp: TextView = root.findViewById(R.id.timestamp)
    val image: ImageView = root.findViewById(R.id.image)

    companion object {
        const val LAYOUT_ID: Int = R.layout.list_item_log
    }
}