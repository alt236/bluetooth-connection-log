package uk.co.alt236.bluetoothconnectionlog.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import uk.co.alt236.bluetoothconnectionlog.BuildConfig
import uk.co.alt236.bluetoothconnectionlog.R
import uk.co.alt236.bluetoothconnectionlog.prefs.DataPrefs
import uk.co.alt236.bluetoothconnectionlog.ui.navigation.Navigator

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var navigator: Navigator
    private lateinit var dataPrefs: DataPrefs

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigator = Navigator(context)
        dataPrefs = DataPrefs(context)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        setupManagePermissionsPref()
        setupVersionPref()
        setupRowLimitPref()
    }

    private fun setupRowLimitPref() {
        val key = getString(R.string.preference_key_event_rows_to_display)
        val pref: ListPreference = findPreference(key)!!
        pref.summary = dataPrefs.getEventRowsToDisplay().toString()
        pref.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                val index = pref.findIndexOfValue(newValue as String?)
                val summary = if (index == -1) {
                    "???"
                } else {
                    pref.entries[index]
                }
                pref.summary = summary
                true
            }
    }

    private fun setupVersionPref() {
        val key = getString(R.string.preference_key_app_version)
        val pref: Preference = findPreference(key)!!
        pref.summary =
            "v${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE}) [${BuildConfig.GIT_HASH_SHORT}]"
    }

    private fun setupManagePermissionsPref() {
        val key = getString(R.string.preference_key_manage_permissions)
        val pref: Preference = findPreference(key)!!
        pref.setOnPreferenceClickListener {
            navigator.openSystemSettings()
            true
        }
    }
}