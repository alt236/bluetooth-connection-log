package uk.co.alt236.bluetoothconnectionlog.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import uk.co.alt236.bluetoothconnectionlog.BuildConfig
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.ui.navigation.Navigator

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var navigator: Navigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigator = Navigator(context)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        setupManagePermissionsPref()
        setupVersionPref()
    }

    private fun setupVersionPref() {
        val key = getString(R.string.preference_key_app_version)
        val pref: Preference = findPreference<Preference>(key) as Preference
        pref.summary = "v${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
    }

    private fun setupManagePermissionsPref() {
        val key = getString(R.string.preference_key_manage_permissions)
        val pref: Preference = findPreference<Preference>(key) as Preference
        pref.setOnPreferenceClickListener {
            navigator.openSystemSettings()
            true
        }
    }
}