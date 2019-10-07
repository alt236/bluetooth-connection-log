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
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry
import uk.co.alt236.bluetoothconnectionlog.ui.LogEntryViewModel
import uk.co.alt236.bluetoothconnectionlog.ui.detail.recycler.LogRecyclerAdapter
import uk.co.alt236.bluetoothconnectionlog.ui.navigation.Navigator

class DeviceDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val macAddress = arguments.getStringOrThrow(ARG_DEVICE_MAC_ADDRESS)
        val deviceName = arguments.getStringOrThrow(ARG_DEVICE_NAME)
        updateTitle(macAddress, deviceName)

        val deviceViewModel = ViewModelProviders.of(this).get(LogEntryViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_device_detail, container, false)
        val recycler = rootView.findViewById<RecyclerView>(R.id.item_list)

        val navigator = Navigator(activity!!)
        val adapter = LogRecyclerAdapter(activity!!, navigator)
        recycler.adapter = adapter

        deviceViewModel.getLogForDevice(macAddress).observe(this,
            Observer<List<LogEntry>> { data ->
                adapter.setData(data)
            })
        return rootView
    }

    private fun updateTitle(macAddress: String, deviceName: CharSequence) {
        if (activity is DeviceDetailActivity) {
            val trimmedName = deviceName.trim()
            val title = if (trimmedName.isBlank()) macAddress else trimmedName
            (activity as DeviceDetailActivity).setPageTitle(title)
        }
    }

    private fun Bundle?.getStringOrThrow(key: String): String {
        checkNotNull(this) { "No Arguments were found" }

        return getString(key)
            ?: throw IllegalArgumentException("No $key was provided.")
    }

    companion object {
        private const val ARG_DEVICE_MAC_ADDRESS = "ARG_DEVICE_MAC_ADDRESS"
        private const val ARG_DEVICE_NAME = "ARG_DEVICE_NAME"

        fun createInstance(macAddress: String, deviceName: CharSequence): Fragment {
            val fragment = DeviceDetailFragment()
            val args = Bundle()

            args.putString(ARG_DEVICE_MAC_ADDRESS, macAddress)
            args.putCharSequence(ARG_DEVICE_NAME, deviceName)

            fragment.arguments = args
            return fragment

        }
    }
}
