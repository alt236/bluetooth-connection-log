package uk.co.alt236.bluetoothconnectionlog.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import uk.co.alt236.bluetoothconnectionlog.map.osm.overlays.OsmFragment

class MapActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.extras?.apply {
            val poi: Poi = getSerializable(ARG_POI) as Poi
            title = poi.title

            if (savedInstanceState == null) {
                val fragment = OsmFragment.createFragment(poi)
                val tag = fragment::class.java.simpleName

                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment, tag)
                    .commit()
            }
        }
    }


    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    companion object {
        private val TAG = MapActivity::class.java.simpleName
        private const val ARG_POI = "ARG_POI"

        fun createIntent(
            context: Context,
            poi: Poi
        ): Intent {
            val intent = Intent(
                context,
                MapActivity::class.java
            )

            intent.putExtra(ARG_POI, poi)
            return intent
        }
    }
}