package uk.co.alt236.bluetoothconnectionlog.map

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log

private const val USE_GOOGLE_MAPS = false

class MapIntentFactory(private val context: Context) {

    fun createIntent(poi: Poi): Intent {
        Log.d(
            MapIntentFactory::class.java.simpleName,
            "Asked to display $poi"
        )

        return if (USE_GOOGLE_MAPS) {
            val uri = Uri.parse("geo:${poi.latitude},${poi.longitude}?z=15")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            intent
        } else {
            MapActivity.createIntent(context, poi)
        }
    }

}
