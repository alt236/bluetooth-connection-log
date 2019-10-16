package uk.co.alt236.bluetoothconnectionlog.ui.detail

import android.os.Bundle
import android.view.*
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

    private lateinit var viewModel: LogEntryViewModel
    private lateinit var btDevice: BtDevice
    private var isFav: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        btDevice = arguments.getSerializableOrThrow(ARG_DEVICE) as BtDevice
        viewModel = ViewModelProviders.of(this).get(LogEntryViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_device_detail, container, false)

        updateTitle(btDevice)
        setHasOptionsMenu(true)

        val recycler = rootView.findViewById<RecyclerView>(R.id.item_list)

        val navigator = Navigator(activity!!)
        val adapter = LogRecyclerAdapter(activity!!, navigator)
        recycler.adapter = adapter

        viewModel.getLogForDevice(btDevice).observe(
            this,
            Observer<List<LogEntry>> { data ->
                adapter.setData(data)
            })

        viewModel.getFavStatusForDevice(btDevice).observe(
            this,
            Observer<Boolean> { fav ->
                isFav = fav
                activity?.invalidateOptionsMenu()
            }
        )
        return rootView
    }

    private fun updateTitle(btDevice: BtDevice) {
        if (activity is DeviceDetailActivity) {
            (activity as DeviceDetailActivity).setPageTitle(btDevice.getDisplayName())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_details, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_not_favorite).isVisible = !isFav
        menu.findItem(R.id.action_favorite).isVisible = isFav
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                viewModel.removeFromFavourites(btDevice)
                true
            }

            R.id.action_not_favorite -> {
                viewModel.addToFavourites(btDevice)
                true
            }

            else -> super.onOptionsItemSelected(item)
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
