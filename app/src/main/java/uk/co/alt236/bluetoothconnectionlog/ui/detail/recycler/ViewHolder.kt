package uk.co.alt236.bluetoothconnectionlog.ui.detail.recycler

import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.ui.views.TwoLinesWithImageListItem

class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
    private val view = TwoLinesWithImageListItem(root)

    fun setTimeStamp(timestamp: String) {
        view.line2.text = timestamp
    }

    fun setActionInfo(actionDescription: ActionDescription) {
        view.line1.text = actionDescription.text
        view.icon.setImageResource(actionDescription.icon)
        view.iconBackground.setImageResource(actionDescription.backgroundIcon)
    }

    fun showLocationButton(show: Boolean) {
        if (show) {
            view.actionIcon.visibility = View.VISIBLE
        } else {
            view.actionIcon.visibility = View.GONE
        }
    }

    data class ActionDescription(
        @ColorRes val color: Int,
        @DrawableRes val icon: Int,
        @DrawableRes val backgroundIcon: Int,
        val text: String
    )

    companion object {
        const val LAYOUT_ID: Int = R.layout.list_item_two_lines_with_icon
    }
}