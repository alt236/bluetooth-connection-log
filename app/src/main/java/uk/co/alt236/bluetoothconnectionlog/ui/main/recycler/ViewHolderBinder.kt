package uk.co.alt236.bluetoothconnectionlog.ui.main.recycler

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.extensions.getInfoFor
import uk.co.alt236.bluetoothconnectionlog.repo.personalisedlog.PersonalisedLogDevice
import uk.co.alt236.bluetoothconnectionlog.ui.detail.DeviceDetailActivity
import uk.co.alt236.bluetoothconnectionlog.ui.detail.DeviceDetailFragment
import uk.co.alt236.btdeviceinfo.BtDeviceClassInfoRepo

internal class ViewHolderBinder(
    private val activity: FragmentActivity,
    private val twoPane: Boolean
) {
    private val deviceInfoRepo = BtDeviceClassInfoRepo()

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
        val info = deviceInfoRepo.getInfoFor(item.logDevice)

        holder.setName(item.logDevice.device.name)
        holder.setMac(item.logDevice.device.macAddress)
        holder.setIcon(info.iconRes)

        setFavourite(holder, item)

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    private fun setFavourite(holder: ViewHolder, item: PersonalisedLogDevice) {
        val color = if (item.favourite) {
            R.color.item_favourite
        } else {
            R.color.item_unfavourite
        }
        holder.setIconBackgroundColor(color)
    }
}