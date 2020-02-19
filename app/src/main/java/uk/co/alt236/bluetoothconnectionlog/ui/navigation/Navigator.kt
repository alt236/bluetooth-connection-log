package uk.co.alt236.bluetoothconnectionlog.ui.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.core.content.ContextCompat
import uk.co.alt236.bluetoothconnectionlog.db.entities.LogEntry
import uk.co.alt236.bluetoothconnectionlog.map.MapIntentFactory
import uk.co.alt236.bluetoothconnectionlog.ui.onboarding.OnBoardingActivity
import uk.co.alt236.bluetoothconnectionlog.ui.settings.SettingsActivity

class Navigator(private val context: Context) {
    private val mapIntentFactory = MapIntentFactory(context.applicationContext)
    private val mapper = LogEntryToPoiMapper(context.applicationContext)

    fun openOnBoarding() {
        val intent = Intent(context, OnBoardingActivity::class.java)
        startActivity(intent)
    }

    fun openAppSettings() {
        val intent = Intent(context, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun openSystemSettings() {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = Uri.parse("package:" + context.packageName)
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
        startActivity(intent)
    }

    fun openMap(entry: LogEntry) {
        require(entry.location.isValid()) { "Passed location is not valid!" }

        val poi = mapper.map(entry)
        val intent = mapIntentFactory.createIntent(poi)
        startActivity(intent)
    }

    private fun startActivity(intent: Intent) {
        if (intent.resolveActivity(context.packageManager) != null) {
            ContextCompat.startActivity(context, intent, null)
        } else {
            Log.e(Navigator::class.java.simpleName, "No activity can handle intent: $intent")
        }
    }
}