package uk.co.alt236.bluetoothconnectionlog.ui.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.db.entities.BtDevice
import uk.co.alt236.bluetoothconnectionlog.ui.main.MainActivity

class DeviceDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_detail)

        val toolbar = findViewById<Toolbar>(R.id.detail_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val btDevice = intent.getSerializableExtra(ARG_DEVICE) as BtDevice
            val fragment = DeviceDetailFragment.createInstance(btDevice)

            supportFragmentManager.beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commit()
        }
    }

    fun setPageTitle(title: CharSequence) {
        setTitle(title)
        supportActionBar?.title = title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                navigateUpTo(Intent(this, MainActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        private const val ARG_DEVICE = "ARG_DEVICE"

        fun createIntent(context: Context, btDevice: BtDevice): Intent {
            val intent = Intent(
                context,
                DeviceDetailActivity::class.java
            )

            intent.putExtra(ARG_DEVICE, btDevice)

            return intent
        }
    }
}
