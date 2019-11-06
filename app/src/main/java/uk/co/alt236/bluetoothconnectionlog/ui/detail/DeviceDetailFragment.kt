package uk.co.alt236.bluetoothconnectionlog.ui.detail

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.db.entities.BtDevice
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry
import uk.co.alt236.bluetoothconnectionlog.extensions.getSerializableOrThrow
import uk.co.alt236.bluetoothconnectionlog.ui.LogEntryViewModel
import uk.co.alt236.bluetoothconnectionlog.ui.detail.recycler.LogRecyclerAdapter
import uk.co.alt236.bluetoothconnectionlog.ui.navigation.Navigator
import uk.co.alt236.bluetoothconnectionlog.ui.views.ProgressDataListView

class DeviceDetailFragment : Fragment() {

    private lateinit var viewModel: LogEntryViewModel
    private lateinit var btDevice: BtDevice
    private var isFav: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        btDevice = arguments.getSerializableOrThrow(ARG_DEVICE) as BtDevice
        viewModel = ViewModelProviders.of(this).get(LogEntryViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_device_detail, container, false)

        updateTitle(btDevice)
        setHasOptionsMenu(true)

        val navigator = Navigator(activity!!)
        val adapter = LogRecyclerAdapter(activity!!, navigator)
        val progressDataListView = rootView.findViewById<ProgressDataListView>(R.id.item_list)
        progressDataListView.setEmptyText(R.string.no_log_entries_empty_text)
        progressDataListView.showProgress()
        progressDataListView.setAdapter(adapter)

        viewModel.getLogForDevice(btDevice).observe(
            this,
            Observer<List<LogEntry>> { data ->
                adapter.setData(data)
                if (data.isEmpty()) {
                    progressDataListView.showEmpty()
                } else {
                    progressDataListView.showData()
                }
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

            R.id.action_show_info -> {
                val infoFragment = DeviceInfoFragment.createInstance(btDevice)
                infoFragment.show(fragmentManager!!, infoFragment::class.java.name)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
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
