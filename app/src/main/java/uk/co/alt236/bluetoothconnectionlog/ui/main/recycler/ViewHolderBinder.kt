package uk.co.alt236.bluetoothconnectionlog.ui.main.recycler

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogDevice
import uk.co.alt236.bluetoothconnectionlog.ui.detail.DeviceDetailActivity
import uk.co.alt236.bluetoothconnectionlog.ui.detail.DeviceDetailFragment
import uk.co.alt236.btdeviceinfo.DeviceIconMapper

internal class ViewHolderBinder(private val activity: FragmentActivity, private val twoPane: Boolean) {
    private val iconMapper = DeviceIconMapper()

    private val onClickListener = View.OnClickListener { v ->
        val item = v.tag as LogDevice

        if (twoPane) {
            val fragment = DeviceDetailFragment.createInstance(item.device)
            activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.item_detail_container, fragment)
                .commit()
        } else {
            val intent = DeviceDetailActivity.createIntent(v.context, item.device)
            ContextCompat.startActivity(v.context, intent, null)
        }
    }

    fun onBindViewHolder(holder: ViewHolder, item: LogDevice) {
        holder.name.text = item.device.name
        holder.macAddress.text = item.device.macAddress
        holder.image.setImageResource(iconMapper.getImage(item))

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    private fun DeviceIconMapper.getImage(item: LogDevice): Int {
        return getImage(
            item.device.bluetoothClass.deviceClass,
            item.device.bluetoothClass.majorDeviceClass
        )
    }
}