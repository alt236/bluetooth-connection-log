package uk.co.alt236.btconnectionlog.sharedprefs.keymatcher

import android.content.SharedPreferences

data class SpecificKeyMatcher(val key: String) : KeyMatcher {
    override fun matches(prefs: SharedPreferences, candidate: String): Boolean {
        return key == candidate
    }
}