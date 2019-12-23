package uk.co.alt236.bluetoothconnectionlog.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import uk.co.alt236.bluetoothconnectionlog.map.gmaps.GmapsFragment
import uk.co.alt236.bluetoothconnectionlog.map.model.Poi
import uk.co.alt236.bluetoothconnectionlog.map.osm.OsmFragment

class MapActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.extras?.apply {
            val poi: Poi = getSerializable(ARG_POI) as Poi
            title = poi.title

            if (savedInstanceState == null) {
                val fragment = if (getBoolean(ARG_GMAPS)) {
                    GmapsFragment.createFragment(poi)
                } else {
                    OsmFragment.createFragment(poi)
                }

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
        private const val ARG_POI = "ARG_POI"
        private const val ARG_GMAPS = "ARG_GMAPS"

        fun createIntent(
            context: Context,
            poi: Poi,
            useGmaps: Boolean = false
        ): Intent {
            val intent = Intent(
                context,
                MapActivity::class.java
            )

            intent.putExtra(ARG_POI, poi)
            intent.putExtra(ARG_GMAPS, useGmaps)

            return intent
        }
    }
}