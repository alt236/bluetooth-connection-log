package uk.co.alt236.bluetoothconnectionlog.map

import android.content.Context
import android.content.Intent
import android.util.Log
import uk.co.alt236.bluetoothconnectionlog.map.model.Poi

private const val USE_GOOGLE_MAPS = true

class MapIntentFactory(private val context: Context) {

    fun createIntent(poi: Poi): Intent {
        Log.d(
            MapIntentFactory::class.java.simpleName,
            "Asked to display $poi"
        )

        return if (USE_GOOGLE_MAPS) {
            MapActivity.createIntent(context, poi, true)
        } else {
            MapActivity.createIntent(context, poi)
        }
    }

}
