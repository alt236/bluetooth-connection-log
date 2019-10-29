package uk.co.alt236.bluetoothconnectionlog.ui.main.recycler

import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.FragmentActivity
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogDevice
import uk.co.alt236.bluetoothconnectionlog.repo.personalisedlog.PersonalisedLogDevice
import uk.co.alt236.bluetoothconnectionlog.ui.detail.DeviceDetailActivity
import uk.co.alt236.bluetoothconnectionlog.ui.detail.DeviceDetailFragment
import uk.co.alt236.btdeviceinfo.DeviceIconMapper

internal class ViewHolderBinder(
    private val activity: FragmentActivity,
    private val twoPane: Boolean
) {
    private val iconMapper = DeviceIconMapper()

    private val onClickListener = View.OnClickListener { v ->
        val item = v.tag as PersonalisedLogDevice

        val device = item.logDevice.device
        if (twoPane) {
            val fragment = DeviceDetailFragment.createInstance(device)
            activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.item_detail_container, fragment)
                .commit()
        } else {
            val intent = DeviceDetailActivity.createIntent(v.context, device)
            ContextCompat.startActivity(v.context, intent, null)
        }
    }

    fun onBindViewHolder(
        holder: ViewHolder,
        item: PersonalisedLogDevice
    ) {
        holder.name.text = item.logDevice.device.name
        holder.macAddress.text = item.logDevice.device.macAddress
        holder.image.setImageResource(iconMapper.getImage(item.logDevice))

        setFavourite(holder, item)

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    private fun setFavourite(
        holder: ViewHolder,
        item: PersonalisedLogDevice
    ) {
        val color = if (item.favourite) {
            ContextCompat.getColor(holder.image.context, R.color.item_favourite)
        } else {
            ContextCompat.getColor(holder.image.context, R.color.item_unfavourite)
        }

        ImageViewCompat.setImageTintList(holder.image, ColorStateList.valueOf(color))
    }

    private fun DeviceIconMapper.getImage(item: LogDevice): Int {
        return getImage(
            item.device.bluetoothClass.deviceClass,
            item.device.bluetoothClass.majorDeviceClass
        )
    }
}