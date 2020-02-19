package uk.co.alt236.bluetoothconnectionlog.ui.detail.recycler

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import uk.co.alt236.bluetoothconnectionlog.R

class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
    val timestamp: TextView = root.findViewById(R.id.timestamp)
    val mapIcon: ImageView = root.findViewById(R.id.map_icon)

    private val action: TextView = root.findViewById(R.id.action)
    private val icon: ImageView = root.findViewById(R.id.icon)
    private val iconBackground: ImageView = root.findViewById(R.id.icon_background)

    fun setActionInfo(actionDescription: ActionDescription) {
        action.text = actionDescription.text
        icon.setImageResource(actionDescription.icon)
        iconBackground.setImageResource(actionDescription.backgroundIcon)
    }

    fun showLocationButton(show: Boolean) {
        if (show) {
            mapIcon.visibility = View.VISIBLE
        } else {
            mapIcon.visibility = View.GONE
        }
    }

    data class ActionDescription(
        @ColorRes val color: Int,
        @DrawableRes val icon: Int,
        @DrawableRes val backgroundIcon: Int,
        val text: String
    )

    companion object {
        const val LAYOUT_ID: Int = R.layout.list_item_log
    }
}