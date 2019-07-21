package uk.co.alt236.bluetoothconnectionlog.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.ui.main.DeviceListActivity

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [DeviceListActivity].
 */
class DeviceDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_detail)

        val toolbar = findViewById<Toolbar>(R.id.detail_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val macAddress = intent.getStringExtra(ARG_DEVICE_MAC_ADDRESS)
            val deviceName = intent.getStringExtra(ARG_DEVICE_NAME)
            val fragment = DeviceDetailFragment.createInstance(macAddress, deviceName)

            supportFragmentManager.beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commit()
        }
    }

    fun setPageTitle(title: CharSequence) {
        setTitle(title)
        supportActionBar?.title = title
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo(Intent(this, DeviceListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    companion object {
        private const val ARG_DEVICE_MAC_ADDRESS = "ARG_DEVICE_MAC_ADDRESS"
        private const val ARG_DEVICE_NAME = "ARG_DEVICE_NAME"

        fun createIntent(context: Context, macAddress: String, deviceName: CharSequence): Intent {
            val intent = Intent(
                context,
                DeviceDetailActivity::class.java
            )

            intent.putExtra(ARG_DEVICE_MAC_ADDRESS, macAddress)
            intent.putExtra(ARG_DEVICE_NAME, deviceName)

            return intent
        }
    }
}
