package uk.co.alt236.bluetoothconnectionlog.ui.main.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import uk.co.alt236.bluetoothconnectionlog.R

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.name)
    val macAddress: TextView = view.findViewById(R.id.macaddress)
    val image: ImageView = view.findViewById(R.id.image)

    companion object {
        const val LAYOUT_ID: Int = R.layout.list_item_device
    }
}