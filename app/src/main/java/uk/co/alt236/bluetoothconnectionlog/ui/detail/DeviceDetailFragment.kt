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

class DeviceDetailFragment : Fragment() {

    private lateinit var macAddress: String

    private lateinit var adapter: LogRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            macAddress = it.getString(ARG_ITEM_ID) ?: throw IllegalArgumentException("No $ARG_ITEM_ID was provided.")
        } ?: throw IllegalArgumentException("No arguments were provided.")

        val rootView = inflater.inflate(R.layout.fragment_device_detail, container, false)
        val recycler = rootView.findViewById<RecyclerView>(R.id.item_list)

        adapter = LogRecyclerAdapter(activity!!)
        recycler.adapter = adapter

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val deviceViewModel = ViewModelProviders.of(this).get(LogEntryViewModel::class.java)

        deviceViewModel.getLogForDevice(macAddress).observe(this,
            Observer<List<LogEntry>> { data ->
                adapter.setData(data)
                updateTitle(data)
            })
    }

    private fun updateTitle(data: List<LogEntry>) {
        if (data.isEmpty()) {
            return
        }

        val item = data[0]
        val title = if (item.device_name.isBlank()) item.mac_address else item.device_name
        (activity as DeviceDetailActivity).setPageTitle(title.trim())
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
