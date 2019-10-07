package uk.co.alt236.bluetoothconnectionlog.ui.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import uk.co.alt236.bluetoothconnectionlog.BuildConfig
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogDevice
import uk.co.alt236.bluetoothconnectionlog.ui.LogEntryViewModel
import uk.co.alt236.bluetoothconnectionlog.ui.main.recycler.DeviceRecyclerAdapter
import uk.co.alt236.bluetoothconnectionlog.ui.navigation.Navigator
import uk.co.alt236.bluetoothconnectionlog.ui.onboarding.OnboardingStatusStore

class MainActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    private lateinit var deviceViewModel: LogEntryViewModel
    private lateinit var permissionCheck: LocationPermissionCheck
    private lateinit var tvLocationStatus: TextView
    private lateinit var tvBtStatus: TextView
    private lateinit var tvItemCount: TextView
    private lateinit var btCheck: BluetoothStatusCheck
    private lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_list)
        permissionCheck = LocationPermissionCheck(this)
        btCheck = BluetoothStatusCheck(this)
        navigator = Navigator(this)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val recyclerView = findViewById<RecyclerView>(R.id.item_list)
        val itemDetailContainer = findViewById<View?>(R.id.item_detail_container)
        val itemStatusContainer = findViewById<View>(R.id.itemStatusContainer)

        setSupportActionBar(toolbar)
        toolbar.title = title

        tvLocationStatus = findViewById(R.id.tvLocationStatus)
        tvBtStatus = findViewById(R.id.tvBluetoothStatus)
        tvItemCount = findViewById(R.id.tvItemCount)

        val adapter = DeviceRecyclerAdapter(
            this,
            twoPane
        )

        deviceViewModel = ViewModelProviders.of(this).get(LogEntryViewModel::class.java)
        deviceViewModel.getAllDevices().observe(this,
            Observer<List<LogDevice>> { data ->
                if (BuildConfig.DEBUG) {
                    val sb = StringBuilder()
                    for (device in data) {
                        sb.append(device.toString())
                        sb.append('\n')
                    }
                    Log.d(MainActivity::class.java.simpleName, "DEVICES: $sb")
                }
                adapter.setData(data!!)
                tvItemCount.text = getString(R.string.formatter_device_count, data.size)
            })

        recyclerView.adapter = adapter

        itemStatusContainer.setOnClickListener { navigator.openSystemSettings() }

        if (itemDetailContainer != null) {
            twoPane = true
        }
    }

    override fun onResume() {
        super.onResume()

        if (btCheck.isBluetoothOn()) {
            tvBtStatus.setText(R.string.on)
        } else {
            tvBtStatus.setText(R.string.off)
        }

        when (permissionCheck.checkLocationPermission()) {
            LocationPermissionCheck.Result.GRANTED -> {
                tvLocationStatus.setText(R.string.permission_granted)
            }
            LocationPermissionCheck.Result.GRANTED_FOREGROUND_ONLY -> {
                tvLocationStatus.setText(R.string.permission_granted_foreground_only)
            }
            LocationPermissionCheck.Result.DENIED -> {
                tvLocationStatus.setText(R.string.permission_denied)
            }
        }

        if (!OnboardingStatusStore(this).hasOnBoarded()) {
            Log.d(TAG, "Need to show onboarding...")
            navigator.openOnBoarding()
        } else {
            Log.d(TAG, "No need to show onboarding...")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        return if (id == R.id.action_settings) {
            navigator.openAppSettings()
            true
        } else super.onOptionsItemSelected(item)
    }

    private companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
