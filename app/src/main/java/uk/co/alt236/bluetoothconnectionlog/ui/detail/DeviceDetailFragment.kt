package uk.co.alt236.bluetoothconnectionlog.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.db.entities.BtDevice
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry
import uk.co.alt236.bluetoothconnectionlog.ui.LogEntryViewModel
import uk.co.alt236.bluetoothconnectionlog.ui.detail.recycler.LogRecyclerAdapter
import uk.co.alt236.bluetoothconnectionlog.ui.navigation.Navigator
import java.io.Serializable

class DeviceDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val btDevice: BtDevice = arguments.getSerializableOrThrow(ARG_DEVICE) as BtDevice
        updateTitle(btDevice)

        val deviceViewModel = ViewModelProviders.of(this).get(LogEntryViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_device_detail, container, false)
        val recycler = rootView.findViewById<RecyclerView>(R.id.item_list)

        val navigator = Navigator(activity!!)
        val adapter = LogRecyclerAdapter(activity!!, navigator)
        recycler.adapter = adapter

        deviceViewModel.getLogForDevice(btDevice.macAddress).observe(
            this,
            Observer<List<LogEntry>> { data ->
                adapter.setData(data)
            })
        return rootView
    }

    private fun updateTitle(btDevice: BtDevice) {
        if (activity is DeviceDetailActivity) {
            val trimmedName = btDevice.name.trim()
            val title = if (trimmedName.isBlank()) btDevice.macAddress else trimmedName
            (activity as DeviceDetailActivity).setPageTitle(title)
        }
    }

    private fun Bundle?.getSerializableOrThrow(key: String): Serializable {
        checkNotNull(this) { "No Arguments were found" }

        return getSerializable(key)
            ?: throw IllegalArgumentException("No $key was provided.")
    }

    companion object {
        private const val ARG_DEVICE = "ARG_DEVICE"

        fun createInstance(btDevice: BtDevice): Fragment {
            val fragment = DeviceDetailFragment()
            val args = Bundle()

            args.putSerializable(ARG_DEVICE, btDevice)

            fragment.arguments = args
            return fragment

        }
    }
}
