package uk.co.alt236.bluetoothconnectionlog.ui.main.recycler

import android.content.res.ColorStateList
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.ui.views.TwoLinesWithImageListItem

class ViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
    private val view = TwoLinesWithImageListItem(root)

    fun setName(name: String) {
        view.line1.text = name
    }

    fun setMac(mac: String) {
        view.line2.text = mac
    }

    fun setIcon(@DrawableRes icon: Int) {
        view.icon.setImageResource(icon)
    }

    fun setIconBackgroundColor(@ColorRes color: Int) {
        val imageView = view.iconBackground
        val colorInt = ContextCompat.getColor(imageView.context, color)

        imageView.setImageResource(R.drawable.ic_action_marker_white)
        ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(colorInt))
    }

    companion object {
        const val LAYOUT_ID: Int = R.layout.list_item_two_lines_with_icon
    }
}