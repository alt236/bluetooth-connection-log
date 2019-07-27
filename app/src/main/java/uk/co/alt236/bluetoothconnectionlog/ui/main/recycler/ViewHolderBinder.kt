package uk.co.alt236.bluetoothconnectionlog.ui.main.recycler

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogDevice
import uk.co.alt236.bluetoothconnectionlog.ui.detail.DeviceDetailActivity
import uk.co.alt236.bluetoothconnectionlog.ui.detail.DeviceDetailFragment

internal class ViewHolderBinder(private val activity: FragmentActivity, private val twoPane: Boolean) {
    private val iconMapper = DeviceIconMapper()

    private val onClickListener = View.OnClickListener { v ->
        val item = v.tag as LogDevice

        if (twoPane) {
            val fragment = DeviceDetailFragment.createInstance(item.mac_address, item.device_name)
            activity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.item_detail_container, fragment)
                .commit()
        } else {
            val intent = DeviceDetailActivity.createIntent(v.context, item.mac_address, item.device_name)
            ContextCompat.startActivity(v.context, intent, null)
        }
    }

    fun onBindViewHolder(holder: ViewHolder, item: LogDevice) {
        holder.name.text = item.device_name
        holder.macAddress.text = item.mac_address
        holder.image.setImageResource(iconMapper.getImage(item))

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }
}