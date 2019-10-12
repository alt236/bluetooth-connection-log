package uk.co.alt236.btconnectionlog.sharedprefs

import android.content.SharedPreferences
import uk.co.alt236.btconnectionlog.sharedprefs.keymatcher.AnyKeyMatcher

class SharedPreferenceKeysLiveData(prefs: SharedPreferences) :
    SharedPreferenceLiveData<Set<String>>(prefs, AnyKeyMatcher(), emptySet()) {

    override fun getValueFromPreferences(key: String, defValue: Set<String>): Set<String> {
        return sharedPrefs.all.keys
    }
}