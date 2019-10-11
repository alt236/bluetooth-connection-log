package uk.co.alt236.bluetoothconnectionlog.ui.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import uk.co.alt236.bluetoothconnectionlog.db.entities.Location
import uk.co.alt236.bluetoothconnectionlog.ui.map.MapActivity

private const val USE_GOOGLE_MAPS = false

class MapIntentFactory(private val context: Context) {

    fun createIntent(deviceName: String, location: Location): Intent {
        require(location.valid) { "Passed location is not valid!" }

        Log.d(
            MapIntentFactory::class.java.simpleName,
            "Asked to display ${location.latitude}/${location.longitude}/${location.horizontalAccuracy}"
        )

        return if (USE_GOOGLE_MAPS) {
            val uri = Uri.parse("geo:${location.latitude},${location.longitude}?z=15")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            intent
        } else {
            MapActivity.createIntent(context, deviceName, location)
        }
    }

}
