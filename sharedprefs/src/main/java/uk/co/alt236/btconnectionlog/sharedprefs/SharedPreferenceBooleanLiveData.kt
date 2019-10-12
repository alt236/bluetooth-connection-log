package uk.co.alt236.btconnectionlog.sharedprefs

import android.content.SharedPreferences
import uk.co.alt236.btconnectionlog.sharedprefs.keymatcher.SpecificKeyMatcher

class SharedPreferenceBooleanLiveData(prefs: SharedPreferences, key: String, defValue: Boolean) :
    SharedPreferenceLiveData<Boolean>(prefs, SpecificKeyMatcher(key), defValue) {

    override fun getValueFromPreferences(key: String, defValue: Boolean): Boolean {
        return sharedPrefs.getBoolean(key, defValue)
    }
}