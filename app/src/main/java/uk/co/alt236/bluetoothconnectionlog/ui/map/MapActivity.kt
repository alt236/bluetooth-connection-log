package uk.co.alt236.bluetoothconnectionlog.ui.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.config.Configuration
import org.osmdroid.views.MapView
import uk.co.alt236.bluetoothconnectionlog.BuildConfig
import uk.co.alt236.bluetoothconnectionlog.R

class MapActivity : AppCompatActivity() {

    private lateinit var map: MapView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val ctx = applicationContext
        Configuration.getInstance().load(
            ctx,
            getSharedPreferences(MAP_PREFERENCES, Context.MODE_PRIVATE)
        )

        setContentView(R.layout.activity_map)

        map = findViewById(R.id.map)
        val mapWrapper = MapWrapper(map)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.extras?.apply {
            val poi: Poi = getSerializable(ARG_POI) as Poi

            Log.d(TAG, "Will display ${poi.latitude}/${poi.longitude}/${poi.accuracy}")

            title = poi.title
            mapWrapper.centerOn(poi)
        }
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
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
        private const val MAP_PREFERENCES = BuildConfig.APPLICATION_ID + ".OSM_MAP_PREFERENCES"

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