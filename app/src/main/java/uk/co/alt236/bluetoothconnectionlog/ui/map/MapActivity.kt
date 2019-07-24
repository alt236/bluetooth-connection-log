package uk.co.alt236.bluetoothconnectionlog.ui.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MinimapOverlay
import org.osmdroid.views.overlay.OverlayItem
import uk.co.alt236.bluetoothconnectionlog.BuildConfig
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.db.entities.Location
import uk.co.alt236.bluetoothconnectionlog.ui.navigation.MapIntentFactory

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
        setupMapView(map)

        intent.extras?.apply {
            val latitude = getDouble(ARG_LAT)
            val longitude = getDouble(ARG_LONG)
            val accuracy = getFloat(ARG_ACCURACY)

            Log.d(MapIntentFactory::class.java.simpleName, "Will display $latitude/$longitude/$accuracy")

            val geoPoint = AccurateGeoPoint(
                aLatitude = latitude,
                aLongitude = longitude,
                aAccuracyInMeters = accuracy
            )

            val items = ArrayList<OverlayItem>()
            items.add(OverlayItem("Title", "Description", geoPoint))

            val overlay = CirclePlottingOverlay2(this@MapActivity, items, null)

            map.overlays.add(overlay)
            map.controller.animateTo(geoPoint)
            map.controller.zoomTo(18.0)
        }
    }

    private fun setupMapView(map: MapView) {
        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        map.setMultiTouchControls(true)

        val minimapOverlay = MinimapOverlay(this, map.tileRequestCompleteHandler)
        minimapOverlay.zoomDifference = 5
        map.overlays.add(minimapOverlay)
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    companion object {
        private const val ARG_LAT = "ARG_LAT"
        private const val ARG_LONG = "ARG_LONG"
        private const val ARG_ACCURACY = "ARG_ACCURACY"
        private const val MAP_PREFERENCES = BuildConfig.APPLICATION_ID + ".OSM_MAP_PREFERENCES"

        fun createIntent(context: Context, location: Location): Intent {
            val intent = Intent(
                context,
                MapActivity::class.java
            )

            intent.putExtra(ARG_LAT, location.latitude)
            intent.putExtra(ARG_LONG, location.longitude)
            intent.putExtra(ARG_ACCURACY, location.horizontalAccuracy)

            return intent
        }
    }
}