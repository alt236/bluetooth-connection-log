package uk.co.alt236.bluetoothconnectionlog.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import uk.co.alt236.bluetoothconnectionlog.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}