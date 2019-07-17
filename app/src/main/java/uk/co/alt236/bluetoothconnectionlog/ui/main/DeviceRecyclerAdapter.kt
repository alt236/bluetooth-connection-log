package uk.co.alt236.bluetoothconnectionlog.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogDevice
import uk.co.alt236.bluetoothconnectionlog.ui.detail.DeviceDetailActivity
import uk.co.alt236.bluetoothconnectionlog.ui.detail.DeviceDetailFragment

class DeviceRecyclerAdapter(
    private val parentActivity: FragmentActivity,
    private val twoPane: Boolean
) :
    RecyclerView.Adapter<DeviceRecyclerAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener
    private var data: List<LogDevice> = ArrayList()

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as LogDevice

            if (twoPane) {
                val fragment = DeviceDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(DeviceDetailFragment.ARG_ITEM_ID, item.mac_address)
                    }
                }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(
                    v.context,
                    DeviceDetailActivity::class.java
                ).apply {
                    putExtra(DeviceDetailFragment.ARG_ITEM_ID, item.mac_address)
                }

                ContextCompat.startActivity(v.context, intent, null)
            }
        }
    }

    fun setData(data: List<LogDevice>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(LAYOUT_ID, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.idView.text = item.device_name
        holder.contentView.text = item.mac_address

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = data.size

    companion object {
        private const val LAYOUT_ID: Int = R.layout.list_item_device
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.id_text)
        val contentView: TextView = view.findViewById(R.id.content)
    }
}