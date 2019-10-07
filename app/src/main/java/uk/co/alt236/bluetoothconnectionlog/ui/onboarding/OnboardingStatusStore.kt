package uk.co.alt236.bluetoothconnectionlog.ui.onboarding

import android.content.Context
import uk.co.alt236.bluetoothconnectionlog.BuildConfig

class OnboardingStatusStore(context: Context) {
    private val prefs = context.getSharedPreferences(STORE_NAME, Context.MODE_PRIVATE)


    fun hasOnBoarded(): Boolean {
        return prefs.getLong(KEY_ONBOARDED_ON_VERSION, -1) > 0
    }

    fun setOnBoardedVersion(version: Long) {
        prefs.edit().putLong(KEY_ONBOARDED_ON_VERSION, version).apply()
    }

    private companion object {
        private const val STORE_NAME = BuildConfig.APPLICATION_ID + ".ONBOARDING_STATUS"
        private const val KEY_ONBOARDED_ON_VERSION = "onboarded_version"
    }
}