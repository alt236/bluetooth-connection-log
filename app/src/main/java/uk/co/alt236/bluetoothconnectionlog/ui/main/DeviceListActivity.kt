package uk.co.alt236.bluetoothconnectionlog.ui.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogDevice
import uk.co.alt236.bluetoothconnectionlog.ui.LogEntryViewModel
import uk.co.alt236.bluetoothconnectionlog.ui.detail.DeviceDetailActivity

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [DeviceDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class DeviceListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    private lateinit var deviceViewModel: LogEntryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val recyclerView = findViewById<RecyclerView>(R.id.item_list)
        val itemDetailContainer = findViewById<View?>(R.id.item_detail_container)

        setSupportActionBar(toolbar)
        toolbar.title = title

        val adapter = DeviceRecyclerAdapter(
            this,
            twoPane
        )

        deviceViewModel = ViewModelProviders.of(this).get(LogEntryViewModel::class.java)
        deviceViewModel.getAllDevices().observe(this,
            Observer<List<LogDevice>> { data -> adapter.setData(data!!) })

        recyclerView.adapter = adapter

        if (itemDetailContainer != null) {
            twoPane = true
        }
    }

}
