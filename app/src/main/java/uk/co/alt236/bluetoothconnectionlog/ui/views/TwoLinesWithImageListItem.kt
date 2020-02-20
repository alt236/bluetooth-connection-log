package uk.co.alt236.bluetoothconnectionlog.ui.views

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import uk.co.alt236.bluetoothconnectionlog.R

class TwoLinesWithImageListItem(val root: View) {
    val line1: TextView = root.findViewById(R.id.line1)
    val line2: TextView = root.findViewById(R.id.line2)
    val actionIcon: ImageView = root.findViewById(R.id.action_icon)
    val icon: ImageView = root.findViewById(R.id.icon)
    val iconBackground: ImageView = root.findViewById(R.id.icon_background)
}